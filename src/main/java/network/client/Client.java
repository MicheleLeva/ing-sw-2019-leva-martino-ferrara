package network.client;

import model.player.PlayerColor;
import network.ClientConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Observable;
import view.View;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class of the client.
 * It works both as main thread and socket manager.
 * @author Michele Leva
 */
public class Client extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private boolean active = true;

    private boolean nameAccepted = false;

    private View view;

    private String ip;

    private int port;

    private boolean isActive(){
        return active;
    }

    /**
     * Constructor for the Client class. It initializes the network parameters from JSON and the socket.
     */
    public Client(){
        JSONParser parser = new JSONParser();
        try {
            JSONObject myJo;
            try {
                Object obj = parser.parse(new FileReader("src/resources/client.json"));
                myJo = (JSONObject) obj;
            } catch (FileNotFoundException e) {
                InputStream configStream = this.getClass().getResourceAsStream("/client.json");
                myJo = (JSONObject)parser.parse(
                        new InputStreamReader(configStream, StandardCharsets.UTF_8));
            }
            JSONArray myArray = (JSONArray) myJo.get("client");
            JSONObject temp = (JSONObject)myArray.get(0);
            ip = (String)temp.get("ip");
            port = ((Long)temp.get("port")).intValue();

        } catch (IOException  | ParseException e) {
            e.printStackTrace();
        }
        try {
            socket = new Socket(ip, port);
            System.out.println("Server found!");
        } catch (IOException e) {
            System.out.println("The connection to the server has been closed!");
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
            //todo
            while (!nameAccepted){
                System.out.println("Insert your name!");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                if (!name.equals("")){
                    this.asyncSend(name);
                    String read = in.nextLine();
                    if (read.equals("ok")){
                        nameAccepted = true;
                    } else{
                        System.out.println("Name already taken!");
                    }
                } else {
                    System.out.println("Invalid name!");
                }
            }
            //todo commentato per velocizzare test
            String read;
            new Thread(() -> view.startScanner()).start();
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.out.println("The connection has been closed!");
            System.out.println("If you want to play a new game or reconnect restart the client.");
        }

        networkHandler.getRemoteActionHandler().deregister(playerColor);
        networkHandler.getRemoteWeaponHandler().deregister(playerColor);
        networkHandler.getRemoteGameHandler().deregister(playerColor);
        networkHandler.getRemotePowerUpHandler().deregister(playerColor);

        view.getActionView().deregister(networkHandler.getRemoteActionHandler());
        view.getWeaponView().deregister(networkHandler.getRemoteWeaponHandler());
        view.getGameView().deregister(networkHandler.getRemoteGameHandler());
        view.getPowerUpView().deregister(networkHandler.getRemotePowerUpHandler());
    }

    public static void main(String[] args) {
        Client client;
        client = new Client();
        client.run();
    }

}
