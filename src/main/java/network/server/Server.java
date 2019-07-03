package network.server;

import controller.ActionController;
import controller.PowerUpController;
import controller.WeaponController;
import model.game.CLI;
import model.game.Game;
import model.game.Model;
import model.player.Player;
import model.player.PlayerColor;
import network.ClientConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.observable.PowerUpObservable;
import utils.observer.ActionObserver;
import utils.observer.PowerUpObserver;
import utils.observer.WeaponObserver;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class which manages the games, the connections and contains the main thread.
 */
public class Server {
    private ServerSocket serverSocket;

    private int PORT;

    private boolean isServerActive = false;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private  ArrayList<Game> games = new ArrayList<>();

    private LinkedHashMap<String, ClientConnection> playerLobby = new LinkedHashMap<>();

    private LinkedHashMap<String, ClientConnection> waitingConnection = new LinkedHashMap<>(); //Players waiting for a game

    private LinkedHashMap<Integer, ArrayList<ClientConnection>> playingGroup = new LinkedHashMap<>(); //Players currently playing

    private LinkedHashMap<String, RemoteView> playerViews = new LinkedHashMap<>(); //List of views for game reconnection

    private int lastID;

    private boolean isTimerOn;

    private long serverTimer;
    private int skulls;

    private void setServerActive(boolean serverActive) {
        isServerActive = serverActive;
    }

    //todo usato per test
    public LinkedHashMap<String, ClientConnection> getWaitingConnection() {
        return waitingConnection;
    }

    /**
     * Prints the state of the server lobbies
     */
    private void serverState(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(CLI.getColor(PlayerColor.GREEN));
        stringBuilder.append("Players in the lobby:\n");
        for (Map.Entry<String, ClientConnection> entry : playerLobby.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" | ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Players in the waiting room:\n");
        for (Map.Entry<String, ClientConnection> entry : waitingConnection.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" | ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Playing groups:\n");
        for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingGroup.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" | ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Games:\n");
        for(Game game : games){
            stringBuilder.append(game.getGameID());
            stringBuilder.append(" | ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Remote Views:\n");
        for (Map.Entry<String, RemoteView> entry : playerViews.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" | ");
        }
        stringBuilder.append(CLI.getResetString());
        System.out.println(stringBuilder.toString());
    }

    /**
     * Reconnects a player to the correct game after their disconnection.
     * @param c new ClientConnection of the player.
     * @param name of the player.
     */
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
            playingGroup.get(pool).add(c);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        serverState();
    }

    /**
     * Deregister a group of players from the server, called after a game has ended
     * @param id of the game.
     */
    private synchronized void deregisterGroup(int id){
        System.out.println("Deregistering pool " + id + " !");
        ArrayList<ClientConnection> clientConnections = new ArrayList<>(playingGroup.get(id));
        for (ClientConnection clientConnection : clientConnections){
            deregisterConnection(clientConnection);
        }
        playingGroup.remove(id);
        serverState();
    }

    /**
     * Deregister a player from the server removing all instances of their connection.
     * @param c ClientConnection of the player.
     */
    public synchronized void deregisterConnection(ClientConnection c) {
        for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingGroup.entrySet()){
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

    /**
     * Searches the current games to set the given player afk.
     * @param name of the player.
     */
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

    /**
     * Checks if a name is already registered in the playerLobby
     * @param name to be checked.
     * @return true if the name is available, false if it's not.
     */
    public boolean nameAvailable(String name){
        for (Map.Entry<String, ClientConnection> entry : playerLobby.entrySet()){
            if (entry.getKey().equals(name)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a player with the given name is currently afk in one of the games.
     * @param name to be checked.
     * @return true if there is an afk player with the given name, false if there isn't.
     */
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

    /**
     * Constructor class of the server. Initializes server parameters from the JSON and the server socket.
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
    public Server() throws IOException {
        JSONParser parser = new JSONParser();
        try {
            JSONObject myJo;
            try {
                Object obj = parser.parse(new FileReader("src/resources/constants.json"));
                myJo = (JSONObject) obj;
            } catch (FileNotFoundException e) {
                InputStream configStream = this.getClass().getResourceAsStream("/constants.json");
                myJo = (JSONObject)parser.parse(
                        new InputStreamReader(configStream, StandardCharsets.UTF_8));
            }
            JSONArray myArray = (JSONArray) myJo.get("constants");
            JSONObject temp = (JSONObject)myArray.get(0);
            serverTimer = (long) temp.get("ServerTimer");
            skulls = ((Long) temp.get("Skulls")).intValue();
            PORT = ((Long)temp.get("serverPort")).intValue();

        } catch (IOException  | ParseException e) {
            e.printStackTrace();
        }
        this.serverSocket = new ServerSocket(PORT);
        System.out.println(InetAddress.getLocalHost());
    }

    /**
     * Adds a player to the lobby and the waiting room for a new game.
     * @param c ClientConnection of the player.
     * @param name of the player.
     */
    public void  addPlayer(ClientConnection c, String name){
        waitingConnection.put(name, c);
        playerLobby.put(name, c);
        checkConnected();
        serverState();
    }

    /**
     * Checks if enough players are in the waiting room to start a timer for the creation of a new game.
     */
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

    /**
     * Timer method called before creating a new game.
     * If it reaches its end  a new game will be started with the players currently in the waiting room.
     * It will start a new game if at any moment during the countdown five players are detected in the waiting room.
     * It will be interrupted at any moment if there are less than three players in the waiting room.
     */
    private void startTimer(){
        isTimerOn = true;
        Timer timer = new Timer();
        TimerTask turnTimerOff = new TimerTask() {
            @Override
            public void run() {
                isTimerOn = false;
            }
        };
        timer.schedule(turnTimerOff, serverTimer);
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
        //when the timer ends it creates a new game
    }

    /**
     * Method for the creation of the game.
     * It creates the necessary classes for the management of the connections and the game.
     * It saves the necessary instances for a possible reconnection the attributes of the server.
     * At the end of the initializations it starts the Game and clears the waiting room.
     */
    private void createGame(){
        System.out.println("Starting a game!");
        if (playingGroup.isEmpty()) {
            lastID = 1;
        } else {
            boolean idFound = false;
            for (int i = 1; !idFound; i++){
                idFound = true;
                lastID = i;
                for (Map.Entry<Integer, ArrayList<ClientConnection>> entry : playingGroup.entrySet()) {
                    if (i == entry.getKey()) {
                        idFound = false;
                        break;
                    }
                }
            }
        }
        playingGroup.put(lastID, new ArrayList<>());

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (String key : keys){
            playingGroup.get(lastID).add(waitingConnection.get(key));
        }
        ArrayList<RemoteView> remoteViews= new ArrayList<>();
        ArrayList<Player> players= new ArrayList<>();

        players.add(new Player(keys.get(0), PlayerColor.BLUE));
        RemoteView blueView = new RemoteView(PlayerColor.BLUE, playingGroup.get(lastID).get(0));
        remoteViews.add(blueView);
        playerViews.put(keys.get(0), blueView);

        players.add(new Player(keys.get(1), PlayerColor.GREEN));
        RemoteView greenView = new RemoteView(PlayerColor.GREEN, playingGroup.get(lastID).get(1));
        remoteViews.add(greenView);
        playerViews.put(keys.get(1), greenView);

        players.add(new Player(keys.get(2), PlayerColor.PURPLE));
        RemoteView purpleView = new RemoteView(PlayerColor.PURPLE, playingGroup.get(lastID).get(2));
        remoteViews.add(purpleView);
        playerViews.put(keys.get(2), purpleView);

        if (waitingConnection.size()> 3){
            players.add(new Player(keys.get(3), PlayerColor.YELLOW));
            RemoteView yellowView = new RemoteView(PlayerColor.YELLOW, playingGroup.get(lastID).get(3));
            remoteViews.add(yellowView);
            playerViews.put(keys.get(3), yellowView);
        }

        if (waitingConnection.size()> 4){
            players.add(new Player(keys.get(4), PlayerColor.GREY));
            RemoteView greyView = new RemoteView(PlayerColor.GREY, playingGroup.get(lastID).get(4));
            remoteViews.add(greyView);
            playerViews.put(keys.get(4), greyView);
        }

        Model model = new Model(players, skulls);

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
        serverState();
    }

    /**
     * Method called from the Game class at the end of the game.
     * It clears the server from all instances of the game and its players.
     * @param id of the game to be closed.
     */
    public void closeGame(int id){
        Game currentGame = null;
        for (Game game : games){
            if (game.getGameID() == id){
                currentGame = game;
                deregisterGroup(id);
                ArrayList<String> playerNames = new ArrayList<>();
                for (Player player : game.getModel().getEachPlayer()){
                    currentGame.getModel().getGameNotifier().deregister(player.getPlayerColor());
                    currentGame.getModel().getActionNotifier().deregister(player.getPlayerColor());
                    currentGame.getModel().getPowerUpNotifier().deregister(player.getPlayerColor());
                    currentGame.getModel().getWeaponNotifier().deregister(player.getPlayerColor());
                    playerNames.add(player.getPlayerName());
                }
                for (String name : playerNames){
                    RemoteView remoteView = playerViews.get(name);
                    ArrayList<ActionObserver> actionObservers = new ArrayList<>(remoteView.getRemoteActionView().listeners);
                    ArrayList<PowerUpObserver> powerUpObservers = new ArrayList<>(remoteView.getRemotePowerUpView().listeners);
                    ArrayList<WeaponObserver> weaponObservers = new ArrayList<>(remoteView.getRemoteWeaponView().listeners);
                    for (ActionObserver actionObserver : actionObservers){
                        remoteView.getRemoteActionView().deregister(actionObserver);
                    }
                    for (PowerUpObserver powerUpObserver : powerUpObservers){
                        remoteView.getRemotePowerUpView().deregister(powerUpObserver);
                    }
                    for (WeaponObserver weaponObserver : weaponObservers){
                        remoteView.getRemoteWeaponView().deregister(weaponObserver);
                    }
                    playerViews.remove(name);
                }
            }
        }
        if (currentGame != null){
            games.remove(currentGame);
        }
        serverState();
    }

    /**
     * Main thread of the server. Listen to the socket for new Clients and registers them.
     */
    private void run(){
        setServerActive(true);
        System.out.println("The server is running!");
        while(isServerActive){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                System.out.println("Found a client!");
                executor.submit(socketConnection);
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
