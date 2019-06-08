package view;

import model.exchanges.events.*;
import model.exchanges.messages.PlayerMessage;
import network.ClientConnection;
import utils.Observer;
import utils.observable.PowerUpObservable;

public class RemotePowerUpView extends PowerUpObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private View view;

    public RemotePowerUpView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "ChoosePowerUpEvent":
                ChoosePowerUpEvent choosePowerUpEvent = new ChoosePowerUpEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(choosePowerUpEvent));
                break;
            case "ChooseTeleporterSquareEvent":
                System.out.println("teleportter1");
                ChooseTeleporterSquareEvent chooseTeleporterSquareEvent = new ChooseTeleporterSquareEvent(view, Integer.parseInt(inputs[1]));
                System.out.println("teleporter2");
                listeners.forEach(l -> l.update(chooseTeleporterSquareEvent));
                break;
            case "ChooseNewtonOpponentEvent":
                ChooseNewtonOpponentEvent chooseNewtonOpponentEvent = new ChooseNewtonOpponentEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(chooseNewtonOpponentEvent));
                break;
            case "ChooseNewtonSquareEvent":
                ChooseNewtonSquareEvent chooseNewtonSquareEvent = new ChooseNewtonSquareEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(chooseNewtonSquareEvent));
                break;
            case "TargetingScopeEvent":
                TargetingScopeEvent targetingScopeEvent = new TargetingScopeEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(targetingScopeEvent));
                break;
            case "TargetingScopeSelectionEvent":
                TargetingScopeSelectionEvent targetingScopeSelectionEvent = new TargetingScopeSelectionEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(targetingScopeSelectionEvent));
                break;
            case "TagbackGrenadeEvent":
                TagbackGrenadeEvent tagbackGrenadeEvent = new TagbackGrenadeEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(tagbackGrenadeEvent));
                break;
            case "DiscardPowerUpEvent":
                DiscardPowerUpEvent discardPowerUpEvent = new DiscardPowerUpEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(discardPowerUpEvent));
                break;
        }
    }

    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("POWERUP," + message.toString());
    }
}
