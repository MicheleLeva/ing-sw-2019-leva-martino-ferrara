package view;

import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;


import java.util.Arrays;

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

    public RemoteView(PlayerColor playerColor, ClientConnection c) {

        super(playerColor);
        this.clientConnection=c;
        c.register(this);
        this.remoteActionView = new RemoteActionView(c, this);
        this.remoteGameView = new RemoteGameView(c, this);
        this.remotePowerUpView = new RemotePowerUpView(c, this);
        this.remoteWeaponView = new RemoteWeaponView(c, this);
    }

    public void update(String message) {
        //System.out.println("Received " + messages);
        try{
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








}
