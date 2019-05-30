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
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            String read = in.nextLine();
            playerName = read;
            int id = server.nameAvailable(playerName);
            if (id != 0) {
                //todo controllare che il giocatore con lo stesso nome sia afk
                //conviene salvare sul server il model direttamente invece dei playernames
                int index = server.getPlayerNames().get(id).indexOf(playerName);
                this.register(server.getPlayerViews().get(id).get(index));
                server.reconnectPlayer(this, id, index);
            } else {
                server.addPlayer(this, playerName);
            }
            System.out.println("The player added their name!");
            asyncSend("GenericMessage,Connected to the server!");
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

    private void send(String message) {
        out.println(message);
        out.flush();
    }

    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void closeConnection() { //mando messaggio al network.client e chiudo la connessione
        send("GenericMessage,The connection has been closed");
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