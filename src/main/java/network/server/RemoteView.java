package network.server;

import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import view.View;


import java.util.Arrays;

/**
 * Class that obtains the message from the socket and sends it to the correct remote view to be handled.
 * @author Michele Leva
 */
public class RemoteView extends View implements Observer<String> {

    private ClientConnection clientConnection;
    private final RemoteActionView remoteActionView;
    private final RemoteWeaponView remoteWeaponView;
    private final RemotePowerUpView remotePowerUpView;
    private final RemoteGameView remoteGameView;

    public RemoteActionView getRemoteActionView() {
        return remoteActionView;
    }

    public RemoteGameView getRemoteGameView() {
        return remoteGameView;
    }

    public RemotePowerUpView getRemotePowerUpView() {
        return remotePowerUpView;
    }

    public RemoteWeaponView getRemoteWeaponView() {
        return remoteWeaponView;
    }

    /**
     * Method that set a new SocketClientConnection. Called after a reconnection.
     * @param c new SocketClientConnection.
     */
    public void setClientConnection(ClientConnection c) {
        this.clientConnection = c;
        remoteActionView.setClientConnection(c);
        remoteGameView.setClientConnection(c);
        remotePowerUpView.setClientConnection(c);
        remoteWeaponView.setClientConnection(c);
    }

    public RemoteView(PlayerColor playerColor, ClientConnection c) {

        super(playerColor);
        this.clientConnection=c;
        c.register(this);
        this.remoteActionView = new RemoteActionView(c, this);
        this.remoteGameView = new RemoteGameView(c, this);
        this.remotePowerUpView = new RemotePowerUpView(c, this);
        this.remoteWeaponView = new RemoteWeaponView(c, this);
    }

    /**
     * Once received a message from the SocketClientConnection,
     * it deserialize it and sends it to the correct remote view to be processed.
     * @param message to be read.
     */
    public void update(String message) {
        try{
            message = message.replaceAll("°","\n");
            message = message.replaceAll("§", "\r");
            String[] inputs = message.split(",");
            String[] strings = Arrays.copyOfRange(inputs, 1, inputs.length);
            switch(inputs[0]){
                case "ACTION":
                    remoteActionView.stringToMessage(strings);
                    break;
                case "GAME":
                    remoteGameView.stringToMessage(strings);
                    break;
                case "WEAPON":
                    remoteWeaponView.stringToMessage(strings);
                    break;
                case "POWERUP":
                    remotePowerUpView.stringToMessage(strings);
                    break;
                default:
                    System.out.println("Unidentified messages from " + getPlayerColor().toString() + " view: \n" + message);
            }
        }catch(IllegalArgumentException e){
            clientConnection.asyncSend("Error!");
        }
    }

    /**
     * Method called by the controller after an error has been found while reading the input of the client.
     * @param error to be sent to the client.
     */
    @Override
    public void reportError(String error) {
        clientConnection.asyncSend("GAME,GenericMessage," + error);
    }
}
