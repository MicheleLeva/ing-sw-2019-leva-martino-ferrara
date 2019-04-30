package server;

import controller.Controller;
import model.Game;
import model.Model;
import model.player_package.Player;
import model.player_package.PlayerColor;
import view.RemoteView;
import view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345; //da cambiare, magari ottenendolo da json?

    private boolean isServerActive = false;

    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private Map<String, ClientConnection> waitingConnection = new HashMap<>();

    private Map<Integer, ArrayList<ClientConnection>> playingPool = new HashMap<>(); //più tavoli di giocatori

    private ArrayList<ClientConnection> playingConnection = new ArrayList<>(); //singolo tavolo di giocatori

    private boolean isTimerOn = false;

    /*
     * Deregistro una connessione
     */

    public synchronized void deregisterConnection(ClientConnection c) { //da cambiare per il nostro gioco
        //deregistra un pool?
        //deregistra un solo giocatore?
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void addPlayer(ClientConnection c, String name){
        waitingConnection.put(name, c);
        checkConnected(); //controllo di avere abbastanza giocatori per iniziare una partita
    }

    private void createGame(){
        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (String key : keys){
            playingConnection.add(waitingConnection.get(key));
        }
        List<View> playerViews= new ArrayList<>();
        ArrayList<Player> players= new ArrayList<>();

        players.add(new Player(keys.get(0), PlayerColor.BLUE));
        playerViews.add(new RemoteView(PlayerColor.BLUE, playingConnection.get(0)));

        players.add(new Player(keys.get(1), PlayerColor.GREEN));
        playerViews.add(new RemoteView(PlayerColor.GREEN, playingConnection.get(1)));

        players.add(new Player(keys.get(2), PlayerColor.PURPLE));
        playerViews.add(new RemoteView(PlayerColor.PURPLE, playingConnection.get(2)));

        if (playingConnection.size()== 4){
            players.add(new Player(keys.get(3), PlayerColor.YELLOW));
            playerViews.add(new RemoteView(PlayerColor.YELLOW, playingConnection.get(4)));
        }
        if (playingConnection.size()== 5){
            players.add(new Player(keys.get(4), PlayerColor.GREY));
            playerViews.add(new RemoteView(PlayerColor.YELLOW, playingConnection.get(3)));
        }

        Model model = new Model(players, 8);
        Controller controller = new Controller(model);

        for (View v : playerViews){
            model.register(v);
            v.register(controller);
        }

        new Game(0, model);
        //inizializzo il gioco, da rivedere l'assegnazione del gameID
        //magari in base al tavolo?

         waitingConnection.clear();
    }

    private void checkConnected(){
        if (waitingConnection.size()>2 && !isTimerOn){
            isTimerOn = true;
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;

            while (elapsedTime < 30*1000) {
                if (waitingConnection.size() ==5 ){
                    createGame();
                    isTimerOn = false;
                    return;
                }
                elapsedTime = (new Date()).getTime() - startTime;

            }
            createGame();
            isTimerOn = false;
            return;
            //se finisce il timer faccio un createGame
        }
    }

    public void run(){
        while(isServerActive){ //finchè il server è attivo cerco client che vogliono connettersi
            try {
                Socket newSocket = serverSocket.accept(); //trovo il client
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection); //Equivalente a new Thread(c).start();
            } catch (IOException e) {
                System.out.println("Connection error!");
            }
        }
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }



}
