package view;

import model.events.*;
import network.ClientConnection;
import utils.Observer;
import utils.observable.PowerUpObservable;

public class RemotePowerUpView extends PowerUpObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private PlayerView view;

    public RemotePowerUpView(ClientConnection clientConnection, PlayerView view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "ChoosePowerUpEvent":
                ChoosePowerUpEvent choosePowerUpEvent = new ChoosePowerUpEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(choosePowerUpEvent));
                break;
            case "ChooseTeleporterSquareEvent":
                ChooseTeleporterSquareEvent chooseTeleporterSquareEvent = new ChooseTeleporterSquareEvent(view, Integer.parseInt(inputs[1]));
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
        }
    }

    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("POWERUP," + message.toString());
    }
}
