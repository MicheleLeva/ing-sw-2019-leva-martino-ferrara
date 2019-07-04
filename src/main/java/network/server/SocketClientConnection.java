package network.server;

import network.ClientConnection;
import utils.Observable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for the management of the socket connection with a client.
 * @author Michele Leva
 */
public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private Server server;

    private boolean active = true;

    private boolean nameAccepted = false;

    private String playerName;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    /**
     * Main thread of the class.
     * Once accepted the name of the player from the socket it adds the player to the waiting room or makes a reconnection.
     * Then it reads the strings from the socket and notifies them to the RemoteView.
     */
    @Override
    public void run(){
        Scanner in;
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            while (!nameAccepted){
                playerName = in.nextLine();
                if (server.nameAvailable(playerName)){
                    if(server.checkAfk(playerName)){
                        server.reconnectPlayer(this, playerName);
                        System.out.println("The player " + playerName + " is reconnecting!");
                        asyncSend("ok");
                        asyncSend("GAME,GenericMessage,Connected to the server! Waiting for reconnection...");
                    } else {
                        server.addPlayer(this, playerName);
                        System.out.println("The player " + playerName + " entered the lobby!");
                        asyncSend("ok");
                        asyncSend("GAME,GenericMessage,Connected to the server! Waiting for a game...");
                    }
                    nameAccepted = true;
                } else {
                    asyncSend("no");
                }
            }
            String read;
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.out.println("The connection with " + playerName + " has been closed!");
        }finally{
            close();
        }
    }

    /**
     * Changes the message to handle Regular Expressions then sends it through the socket.
     * @param message to send
     */
    private synchronized void send(String message) {
        message = message.replaceAll("\n","°");
        message = message.replaceAll("\r", "§");
        out.println(message);
        out.flush();
    }

    /**
     * Calls the send method.
     * @param message to be sent.
     */
    public synchronized void asyncSend(final String message){
        send(message);
    }

    /**
     * Closes the connection with the client.
     */
    public synchronized void closeConnection() {
        asyncSend("GAME,GenericMessage,The server is closing the connection!");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(playerName + " has disconnected! ");
        }
        active = false;
    }

    /**
     * Signals the server this connection will be closed and then closes the connection.
     */
    private void close() {
        server.setPlayerAFK(playerName);
        closeConnection();
        System.out.println("Deregistering of " + playerName + "'s connection!");
        server.deregisterConnection(this);
    }

}