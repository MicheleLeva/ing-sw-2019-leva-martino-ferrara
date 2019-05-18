package network.server;

import controller.ActionController;
import controller.PowerUpController;
import controller.WeaponController;
import model.Game;
import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import network.ClientConnection;
import view.*;

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

    private Timer timer = new Timer();

    public TimerTask turnTimerOff = new TimerTask() {
        @Override
        public void run() {
            isTimerOn = false;
        }
    };

    /*
     * Deregistro una connessione
     */

    public synchronized void deregisterConnection(ClientConnection c) { //da cambiare per il nostro gioco
        //todo
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
        List<RemoteView> remoteViews= new ArrayList<>();
        ArrayList<Player> players= new ArrayList<>();

        players.add(new Player(keys.get(0), PlayerColor.BLUE));
        remoteViews.add(new RemoteView(PlayerColor.BLUE, playingConnection.get(0)));

        players.add(new Player(keys.get(1), PlayerColor.GREEN));
        remoteViews.add(new RemoteView(PlayerColor.GREEN, playingConnection.get(1)));

        players.add(new Player(keys.get(2), PlayerColor.PURPLE));
        remoteViews.add(new RemoteView(PlayerColor.PURPLE, playingConnection.get(2)));

        if (playingConnection.size()== 4){
            players.add(new Player(keys.get(3), PlayerColor.YELLOW));
            remoteViews.add(new RemoteView(PlayerColor.YELLOW, playingConnection.get(4)));
        }
        if (playingConnection.size()== 5){
            players.add(new Player(keys.get(4), PlayerColor.GREY));
            remoteViews.add(new RemoteView(PlayerColor.YELLOW, playingConnection.get(3)));
        }

        Model model = new Model(players, 8);

        for (RemoteView v : remoteViews){
            model.getGameNotifier().register(v.getPlayerColor(), v.getRemoteGameView());
            model.getActionNotifier().register(v.getPlayerColor(), v.getRemoteActionView());
            model.getPowerUpNotifier().register(v.getPlayerColor(), v.getRemotePowerUpView());
            model.getWeaponNotifier().register(v.getPlayerColor(), v.getRemoteWeaponView());
            v.getRemoteActionView().register(new ActionController(model));
            v.getRemotePowerUpView().register(new PowerUpController(model));
            v.getRemoteWeaponView().register(new WeaponController(model));
        }

        new Game(0, model);
        //inizializzo il gioco, da rivedere l'assegnazione del gameID
        //magari in base al tavolo?

         waitingConnection.clear();
    }

    private void checkConnected(){
        if (waitingConnection.size()>2 && !isTimerOn){
            isTimerOn = true;
            timer.schedule(turnTimerOff, 1000*30);
            while (isTimerOn) {
                if (waitingConnection.size() == 5) {
                    createGame();
                    isTimerOn = false;
                    timer.cancel();
                    return;
                }
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
                executor.submit(socketConnection); //istanzio la connessione su un nuovo thread
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
