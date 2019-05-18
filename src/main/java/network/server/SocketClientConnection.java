package network.server;

import network.ClientConnection;
import network.server.Server;
import utils.Observable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable{

    private Socket socket;

    private PrintStream out;

    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public void run(){
        Scanner in;
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            send("NameSetMessage,Insert your name!");
            String read = in.nextLine();
            name = read;
            //todo sistemare
            server.addPlayer(this, name);
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!");
        }finally{
            close();
        }
    }

    private void send(String message) {
        out.println(message);
        out.flush();
    }

    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void closeConnection() { //mando messaggio al network.client e chiudo la connessione
        send("GenericMessage,The connection has been closed");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error!");
        }
        active = false;
    }

    private void close() { //chiudo la connessione
        closeConnection();
        System.out.println("Deregistering of the connection!");
        server.deregisterConnection(this);
    }

}