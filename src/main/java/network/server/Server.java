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
    private ServerSocket serverSocket;

    private int PORT = 6868;

    private boolean isServerActive = false;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private  ExecutorService games = Executors.newFixedThreadPool( 10);

    private Map<String, ClientConnection> waitingConnection = new HashMap<>();

    private Map<Integer, ArrayList<ClientConnection>> playingPool = new HashMap<>(); //più tavoli di giocatori

    private Map<Integer, ArrayList<String>> playerNames = new HashMap<>();

    private Map<Integer, ArrayList<RemoteView>> playerViews = new HashMap<>();

    private int lastID;

    private ArrayList<ClientConnection> playingConnection = new ArrayList<>(); //singolo tavolo di giocatori

    private boolean isTimerOn = false;

    private Timer timer = new Timer();

    /*private TimerTask turnTimerOff = new TimerTask() {
        @Override
        public void run() {
            isTimerOn = false;
        }
    };*/

    public Map<Integer, ArrayList<RemoteView>> getPlayerViews() {
        return playerViews;
    }

    public Map<Integer, ArrayList<String>> getPlayerNames() {
        return playerNames;
    }

    public void setServerActive(boolean serverActive) {
        isServerActive = serverActive;
    }

    public synchronized void reconnectPlayer(ClientConnection c, int pool, int index){
        playingPool.get(pool).remove(index);
        playingPool.get(pool).add(index, c);
    }

    /*
     * Deregistro una connessione
     */
    public synchronized void deregisterConnection(ClientConnection c) {
        for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingPool.entrySet()){
            if (entry.getValue().contains(c)){
                entry.getValue().remove(c);
                c.closeConnection();
            }
        }
        for (Map.Entry<String, ClientConnection> entry : waitingConnection.entrySet()){
            if (entry.getValue() == c){
                waitingConnection.remove(entry.getKey());
                c.closeConnection();
            }
        }
    }

    public int nameAvailable(String name){
        for (Map.Entry<Integer, ArrayList<String>> entry : playerNames.entrySet()){
            if (entry.getValue().contains(name)){
                return entry.getKey();
            }
        }
        return 0;
    }

    public synchronized void deregisterPool(int id){
        for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingPool.entrySet()){
            if (entry.getKey() == id){
                for (ClientConnection c : entry.getValue()){
                    deregisterConnection(c);
                }
            }
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public synchronized void  addPlayer(ClientConnection c, String name){
        waitingConnection.put(name, c);
        checkConnected();
    }

    private void checkConnected() { //todo sistemare, il gioco non parte!
        if (waitingConnection.size() > 2 && !isTimerOn) {
            isTimerOn = true;
            new Thread() {
                public void run() {
                    startTimer();
                }
            }.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isTimerOn = false;
        }

    }

    private void startTimer(){
        System.out.println("Timer is starting");
        while (isTimerOn) {
            if (waitingConnection.size() == 5) {
                System.out.println("Reached 5 players!");
                createGame();
                isTimerOn = false;
                timer.cancel();
                return;
            }
            if (waitingConnection.size()<3){
                System.out.println("Not enough players to start a game: stopping the timer");
                isTimerOn = false;
                timer.cancel();
                return;
            }
        }
        System.out.println("Time is up!");
        createGame();
        isTimerOn = false;
        //se finisce il timer faccio un createGame
    }

    private void createGame(){
        System.out.println("Starting a game!");
        if (!playingPool.isEmpty()) {
            for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingPool.entrySet()) {
                lastID = entry.getKey();
            }
            lastID ++;
        } else {
            lastID = 1;
        }
        playingPool.put(lastID, new ArrayList<>());
        playerNames.put(lastID, new ArrayList<>());

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (String key : keys){
            playingPool.get(lastID).add(waitingConnection.get(key));
        }
        ArrayList<RemoteView> remoteViews= new ArrayList<>();
        ArrayList<Player> players= new ArrayList<>();

        players.add(new Player(keys.get(0), PlayerColor.BLUE));
        playerNames.get(lastID).add(keys.get(0));
        remoteViews.add(new RemoteView(PlayerColor.BLUE, playingConnection.get(0)));

        players.add(new Player(keys.get(1), PlayerColor.GREEN));
        playerNames.get(lastID).add(keys.get(1));
        remoteViews.add(new RemoteView(PlayerColor.GREEN, playingPool.get(lastID).get(1)));

        players.add(new Player(keys.get(2), PlayerColor.PURPLE));
        playerNames.get(lastID).add(keys.get(2));
        remoteViews.add(new RemoteView(PlayerColor.PURPLE, playingPool.get(lastID).get(2)));

        if (waitingConnection.size()== 4){
            players.add(new Player(keys.get(3), PlayerColor.YELLOW));
            playerNames.get(lastID).add(keys.get(3));
            remoteViews.add(new RemoteView(PlayerColor.YELLOW, playingPool.get(lastID).get(3)));
        }

        if (waitingConnection.size()== 5){
            players.add(new Player(keys.get(4), PlayerColor.GREY));
            playerNames.get(lastID).add(keys.get(4));
            remoteViews.add(new RemoteView(PlayerColor.YELLOW,playingPool.get(lastID).get(4)));
        }

        playerViews.put(lastID, remoteViews);

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

        games.submit(new Game(lastID, model));

         waitingConnection.clear();
    }

    public void run(){
        setServerActive(true);
        System.out.println("The server is running!");
        while(isServerActive){ //finchè il server è attivo cerco client che vogliono connettersi
            try {
                Socket newSocket = serverSocket.accept(); //trovo il client
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                System.out.println("Found a client!");
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
