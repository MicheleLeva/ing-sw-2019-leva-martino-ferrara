package network.client;

import model.events.*;
import model.player_package.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.PowerUpNotify;

public class RemotePowerUpHandler extends PowerUpNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemotePowerUpHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

    public void stringToMessage(String[] inputs){
        switch(inputs[0]) {
            case "ChoosePowerUpMessage":
                ChoosePowerUpMessage choosePowerUpMessage = new ChoosePowerUpMessage(inputs[1]);
                listeners.get(playerColor).update(choosePowerUpMessage);
                break;
            case "ChooseTeleporterSquareMessage":
                ChooseTeleporterSquareMessage chooseTeleporterSquareMessage = new ChooseTeleporterSquareMessage(inputs[1]);
                listeners.get(playerColor).update(chooseTeleporterSquareMessage);
                break;
            case "ChooseNewtonOpponentMessage":
                ChooseNewtonOpponentMessage chooseNewtonOpponentMessage = new ChooseNewtonOpponentMessage(inputs[1]);
                listeners.get(playerColor).update(chooseNewtonOpponentMessage);
                break;
            case "ChooseNewtonSquareMessage":
                ChooseNewtonSquareMessage chooseNewtonSquareMessage = new ChooseNewtonSquareMessage(inputs[1]);
                listeners.get(playerColor).update(chooseNewtonSquareMessage);
                break;
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("POWERUP,"+ message.toString());
    }
}
