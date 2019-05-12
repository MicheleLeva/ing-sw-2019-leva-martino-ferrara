package network.client;

import model.player_package.PlayerColor;
import network.ClientConnection;
import utils.Observable;
import view.PlayerView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private boolean active = true;

    private PlayerView playerView;

    //da recuperare effettivi dati per connessione
    private String ip = "0.0.0.0";

    private int port = 1234;

    private boolean isActive(){
        return active;
    }

    public Client(){
        //magari si importano ip e port dall'input dell'utente?
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("Connection error!");
        }
    }

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closign connection!");
        }
        active = false;
    }

    private void send(String message) {
        out.println(message);
        out.flush();
    }

    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    public void run(){

        //placeholder, c'è davvero bisogno che la PlayerView contega il colore?
        //se sì bisogna implementare la messaggistica
        playerView = new PlayerView(PlayerColor.BLUE);
        Translator translator = new Translator(this);

        translator.register(playerView.getActionView());
        translator.register(playerView.getGameView());
        translator.register(playerView.getPowerUpView());
        translator.register(playerView.getWeaponView());

        Scanner in;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            String read;
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException e) {
            System.out.println("Connection error!");
        }
    }

    public static void main(String[] args) {
        Client client;
        client = new Client();
        client.run();
    }

}
