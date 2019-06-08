package network.server;

import network.ClientConnection;
import utils.Observable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private Server server;

    private boolean active = true;

    private boolean nameAccepted = false;

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
            /*while (!nameAccepted){
                playerName= in.nextLine();
                if (server.nameAvailable(playerName)){
                    if(server.checkAfk(playerName)){
                        server.reconnectPlayer(this, playerName);
                    } else {
                        server.addPlayer(this, playerName);
                    }
                    asyncSend("ok");
                    nameAccepted = true;
                } else {
                    asyncSend("no");
                }
            }
            String read;*/
            //todo modificato per velocizzare test
            int c = 0;
            for (Map.Entry<String, ClientConnection> entry : server.getWaitingConnection().entrySet()){
                if (entry.getKey().equals("Asdrubale")){
                    c ++;
                }
                if (entry.getKey().equals("Bruno")){
                    c ++;
                }
            }
            if (c == 0){
                server.addPlayer(this, "Asdrubale");
            }
            if (c == 1){
                server.addPlayer(this, "Bruno");
            }
            if (c == 2){
                server.addPlayer(this, "Carlo");
            }
            String read;
            //todo modificato per velocizzare test
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
        out.println(message);
        out.flush();
    }

    public synchronized void asyncSend(final String message){
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

    //Close Connection
    private void close() {
        closeConnection();
        System.out.println("Deregistering of the connection!");
        server.deregisterConnection(this);
    }

}