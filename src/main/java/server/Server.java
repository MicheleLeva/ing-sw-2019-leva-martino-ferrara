package server;

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
    }

    public void createGame(){
        //
        //classe che crea i pattern observer-observable
        //associa i colori alle view
        //fa partire Game.init
    }

    public synchronized void checkConnected(){
        if (waitingConnection.size()>2){
            if (waitingConnection.size() ==5 ){
                createGame();
            }
            //faccio partire il timer?
            //se finisce il timer faccio un createGame
        }
    }

    public void run(){
        while(isServerActive){ //finchè il server è attivo cerco client che vogliono connettersi
            try {
                Socket newSocket = serverSocket.accept(); //trovo il client
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection); //Equivalente a new Thread(c).start();
                checkConnected(); //controllo di avere abbastanza giocatori per far partire una partita
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
