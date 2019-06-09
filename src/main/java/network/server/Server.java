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

    private  ArrayList<Game> games = new ArrayList<>();

    private LinkedHashMap<String, ClientConnection> playerLobby = new LinkedHashMap<>();

    private LinkedHashMap<String, ClientConnection> waitingConnection = new LinkedHashMap<>(); //Players waiting for a game

    private LinkedHashMap<Integer, ArrayList<ClientConnection>> playingPool = new LinkedHashMap<>(); //Players currently playing

    private LinkedHashMap<String, RemoteView> playerViews = new LinkedHashMap<>(); //List of views for game reconnection

    private int lastID;

    private boolean isTimerOn;

    public void setServerActive(boolean serverActive) {
        isServerActive = serverActive;
    }

    //todo usato per test
    public LinkedHashMap<String, ClientConnection> getWaitingConnection() {
        return waitingConnection;
    }

    public synchronized void reconnectPlayer(ClientConnection c, String name){
        playerLobby.put(name, c);
        c.register(playerViews.get(name));
        playerViews.get(name).setClientConnection(c);
        int pool = -1;
        for (Game game : games){
            for (Player player : game.getModel().getEachPlayer()){
                if (player.getPlayerName().equals(name) && player.isAfk()){
                    game.getModel().wakeUpPlayer(player);
                    pool = game.getGameID();
                }
            }
        }
        try {
            playingPool.get(pool).add(c);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    //Deregister a game
    public synchronized void deregisterPool(int id){
        System.out.println("Deregistering a pool!");
        ArrayList<ClientConnection> clientConnections = new ArrayList<>(playingPool.get(id));
        for (ClientConnection clientConnection : clientConnections){
            deregisterConnection(clientConnection);
        }
        playingPool.remove(id);

    }

    //Deregistering a single connection
    public synchronized void deregisterConnection(ClientConnection c) {
        for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingPool.entrySet()){
            if (entry.getValue().contains(c)){
                entry.getValue().remove(c);
                c.closeConnection();
            }
        }
        String name ="";
        for (Map.Entry<String, ClientConnection> entry : waitingConnection.entrySet()){
            if (entry.getValue().equals(c)){
                name = entry.getKey();
                c.closeConnection();
            }
        }
        waitingConnection.remove(name);

        for (Map.Entry<String, ClientConnection> entry : playerLobby.entrySet()){
            if (entry.getValue().equals(c)){
                name = entry.getKey();
                c.closeConnection();
            }
        }
        playerLobby.remove(name);
    }

    //Sets the player afk
    public void setPlayerAFK(String name){
        boolean found = false;
        for (Game game : games){
            for (Player player : game.getModel().getEachPlayer()){
                if (player.getPlayerName().equals(name)){
                    found = true;
                    game.getModel().setPlayerAfk(player);
                }
            }
        }
        if (!found){
            System.out.println("The player " + name + " is not playing any game and cannot be set afk!");
        } else {
            System.out.println("The player " + name + " has disconnected!");
        }
    }

    //Checks if a name is available
    public boolean nameAvailable(String name){
        for (Map.Entry<String, ClientConnection> entry : playerLobby.entrySet()){
            if (entry.getKey().equals(name)){
                return false;
            }
        }
        return true;
    }

    //Check if a player is afk
    public boolean checkAfk(String name){
        for (Game game : games){
            for (Player player : game.getModel().getEachPlayer()){
                if (player.getPlayerName().equals(name) && player.isAfk()){
                    return true;
                }
            }
        }
        return false;
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void  addPlayer(ClientConnection c, String name){
        waitingConnection.put(name, c);
        playerLobby.put(name, c);
        checkConnected();
    }

    private void checkConnected() {
        System.out.println("Checking connected");
        System.out.println("Waiting players: " + waitingConnection.size());
        if (waitingConnection.size() > 2 && !isTimerOn) {
            System.out.println("Enough players to start a game!");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startTimer();
                }
            }).start();
        }
    }

    private void startTimer(){
        isTimerOn = true;
        Timer timer = new Timer();
        TimerTask turnTimerOff = new TimerTask() {
            @Override
            public void run() {
                isTimerOn = false;
            }
        };
        timer.schedule(turnTimerOff, 1000L);
        System.out.println("Timer is starting");
        while (isTimerOn) {
            System.out.print("");
            if (waitingConnection.size() == 5) {
                System.out.println("Reached 5 players!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        createGame();
                    }
                }).start();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                createGame();
            }
        }).start();
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

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (String key : keys){
            playingPool.get(lastID).add(waitingConnection.get(key));
        }
        ArrayList<RemoteView> remoteViews= new ArrayList<>();
        ArrayList<Player> players= new ArrayList<>();

        players.add(new Player(keys.get(0), PlayerColor.BLUE));
        RemoteView blueView = new RemoteView(PlayerColor.BLUE, playingPool.get(lastID).get(0));
        remoteViews.add(blueView);
        playerViews.put(keys.get(0), blueView);

        players.add(new Player(keys.get(1), PlayerColor.GREEN));
        RemoteView greenView = new RemoteView(PlayerColor.GREEN, playingPool.get(lastID).get(1));
        remoteViews.add(greenView);
        playerViews.put(keys.get(1), greenView);

        players.add(new Player(keys.get(2), PlayerColor.PURPLE));
        RemoteView purpleView = new RemoteView(PlayerColor.PURPLE, playingPool.get(lastID).get(2));
        remoteViews.add(purpleView);
        playerViews.put(keys.get(2), purpleView);

        if (waitingConnection.size()== 4){
            players.add(new Player(keys.get(3), PlayerColor.YELLOW));
            RemoteView yellowView = new RemoteView(PlayerColor.YELLOW, playingPool.get(lastID).get(3));
            remoteViews.add(yellowView);
            playerViews.put(keys.get(3), yellowView);
        }

        if (waitingConnection.size()== 5){
            players.add(new Player(keys.get(4), PlayerColor.GREY));
            RemoteView greyView = new RemoteView(PlayerColor.YELLOW,playingPool.get(lastID).get(4));
            remoteViews.add(greyView);
            playerViews.put(keys.get(4), greyView);
        }

        Model model = new Model(players, 8); //todo importare il numero di skulls da json

        for (RemoteView v : remoteViews){
            model.getGameNotifier().register(v.getPlayerColor(), v.getRemoteGameView());
            model.getActionNotifier().register(v.getPlayerColor(), v.getRemoteActionView());
            model.getPowerUpNotifier().register(v.getPlayerColor(), v.getRemotePowerUpView());
            model.getWeaponNotifier().register(v.getPlayerColor(), v.getRemoteWeaponView());
            v.getRemoteActionView().register(new ActionController(model));
            v.getRemotePowerUpView().register(new PowerUpController(model));
            v.getRemoteWeaponView().register(new WeaponController(model));
        }

        Game game = new Game(lastID, model, this);
        games.add(game);

        new Thread(new Runnable() {
            @Override
            public void run() {
                 game.run();
            }
        }).start();

        waitingConnection.clear();
        System.out.println("Clearing the waiting room!");
    }

    public void closeGame(int id){
        Game currentGame = null;
        for (Game game : games){
            if (game.getGameID() == id){
                currentGame = game;
                deregisterPool(id);
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : game.getModel().getAllPlayers()){
                    playerNames.add(player.getPlayerName());
                }
                for (String name : playerNames){
                    playerViews.remove(name);
                }
            }
        }
        if (currentGame != null){
            games.remove(currentGame);
        }
        /*System.out.println("Updated list of games: " + games.toString());
        System.out.println("PlayerLobby: " + playerLobby.toString());
        System.out.println("PlayingPool: " + playingPool.toString());
        System.out.println("RemoteViews: " + playerViews.toString());*/
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
