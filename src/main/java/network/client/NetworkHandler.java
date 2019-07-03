package network.client;

import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import java.util.Arrays;

/**
 * Class that obtains the message from the socket and sends it to the correct handler to be handled.
 * @author Michele Leva
 */
public class NetworkHandler implements Observer<String> {

    private final RemoteActionHandler remoteActionHandler;
    private final RemotePowerUpHandler remotePowerUpHandler;
    private final RemoteGameHandler remoteGameHandler;
    private final RemoteWeaponHandler remoteWeaponHandler;

    public NetworkHandler(ClientConnection client){
        //The actual color is saved on the server, this is a placeholder
        remoteActionHandler = new RemoteActionHandler(client, PlayerColor.BLUE);
        remoteGameHandler = new RemoteGameHandler(client, PlayerColor.BLUE);
        remotePowerUpHandler = new RemotePowerUpHandler(client, PlayerColor.BLUE);
        remoteWeaponHandler = new RemoteWeaponHandler(client, PlayerColor.BLUE);
    }

    public RemoteActionHandler getRemoteActionHandler() {
        return remoteActionHandler;
    }

    public RemoteGameHandler getRemoteGameHandler() {
        return remoteGameHandler;
    }

    public RemotePowerUpHandler getRemotePowerUpHandler() {
        return remotePowerUpHandler;
    }

    public RemoteWeaponHandler getRemoteWeaponHandler() {
        return remoteWeaponHandler;
    }

    /**
     * Once received a message from the Client class,
     * it deserialize it and sends it to the correct handler to be processed.
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
                    remoteActionHandler.stringToMessage(strings);
                    break;
                case "GAME":
                    remoteGameHandler.stringToMessage(strings);
                    break;
                case "WEAPON":
                    remoteWeaponHandler.stringToMessage(strings);
                    break;
                case "POWERUP":
                    remotePowerUpHandler.stringToMessage(strings);
                    break;
                default:
                    System.out.println("Unidentified message from the server: \n" + message);
            }
        }catch(IllegalArgumentException e){
            System.out.println("Error reading from stream!");
        }
    }

}
