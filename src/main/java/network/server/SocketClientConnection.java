package network.server;

import network.ClientConnection;
import utils.Observable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private Server server;

    private boolean active = true;

    String playerName;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public void run(){
        Scanner in;
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            String read = in.nextLine();
            playerName = read;
            int id = server.nameAvailable(playerName);
            if (id != 0 && server.checkAfk(playerName)) {
                    int index = server.getPlayerNames().get(id).indexOf(playerName);
                    this.register(server.getPlayerViews().get(id).get(index));
                    server.reconnectPlayer(this, id, index, playerName);

            } else {
                server.addPlayer(this, playerName);
            }
            System.out.println("The player added their name!");
            asyncSend("GAME,GenericMessage,Connected to the server! Waiting for a game...");
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!");
        }finally{
            close();
        }
    }

    private synchronized void send(String message) {
        message = message.replaceAll("\n","°");
        message = message.replaceAll("\r", "§");
        //System.out.println(message);
        out.println(message);
        out.flush();
    }

    public synchronized void asyncSend(final String message){
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();*/
        send(message);
    }

    public synchronized void closeConnection() { //mando messaggio al network.client e chiudo la connessione
        asyncSend("GAME,GenericMessage,The connection has been closed");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error!");
        }
        active = false;
    }

    private void close() { //chiudo la connessione
        closeConnection();
        System.out.println("Deregistering of the connection!");
        server.deregisterConnection(this);
    }

}