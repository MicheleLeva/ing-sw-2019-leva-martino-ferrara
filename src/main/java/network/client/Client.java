package network.client;

import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observable;
import view.View;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class of the client.
 * It works both as main thread and socket manager.
 */
public class Client extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private boolean active = true;

    private boolean nameAccepted = false;

    private View view;

    //da recuperare effettivi dati per connessione
    private String ip = "localhost";

    private int port = 8080;

    private boolean isActive(){
        return active;
    }

    public Client(){
        //magari si importano ip e port dall'input dell'utente?
        try {
            socket = new Socket(ip, port);
            System.out.println("Server found!");
        } catch (IOException e) {
            System.out.println("Connection error!");
        }
    }

    /**
     * Closes the socket connection.
     */
    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection!");
        }
        active = false;
    }

    /**
     * Changes the message to handle Regular Expressions then sends it through the socket.
     * @param message to send
     */
    private void send(String message) {
        message = message.replaceAll("\n","°");
        message = message.replaceAll("\r", "§");
        out.println(message);
        out.flush();
    }

    /**
     * Initializes a thread to send a message.
     * @param message to send
     */
    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    /**
     * Main thread of the client.
     * It initializes the needed classes and starts the CLI for the player.
     * It also reads the socket and notify the message to the NetworkHandler.
     */
    public void run(){

        //The actual color is saved on the server, this is a placeholder
        PlayerColor playerColor = PlayerColor.BLUE;

        view = new View(playerColor);
        NetworkHandler networkHandler = new NetworkHandler(this);

        this.register(networkHandler);

        networkHandler.getRemoteActionHandler().register(playerColor, view.getActionView());
        networkHandler.getRemoteWeaponHandler().register(playerColor, view.getWeaponView());
        networkHandler.getRemoteGameHandler().register(playerColor, view.getGameView());
        networkHandler.getRemotePowerUpHandler().register(playerColor, view.getPowerUpView());

        view.getActionView().register(networkHandler.getRemoteActionHandler());
        view.getWeaponView().register(networkHandler.getRemoteWeaponHandler());
        view.getGameView().register(networkHandler.getRemoteGameHandler());
        view.getPowerUpView().register(networkHandler.getRemotePowerUpHandler());

        Scanner in;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            /*while (!nameAccepted){
                System.out.println("Insert your name!");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                this.asyncSend(name);
                String read = in.nextLine();
                if (read.equals("ok")){
                    nameAccepted = true;
                } else{
                    System.out.println("Name already taken!");
                }
            }*/
            //todo commentato per velocizzare test
            String read;
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.out.println("Connection error!");
        }
    }

    public static void main(String[] args) {
        Client client;
        client = new Client();
        client.run();
    }

}
