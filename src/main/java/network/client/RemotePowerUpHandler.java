package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.*;
import model.player.PlayerColor;
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
            case "DiscardPowerUpMessage":
                DiscardPowerUpMessage discardPowerUpMessage = new DiscardPowerUpMessage(inputs[1]);
                listeners.get(playerColor).update(discardPowerUpMessage);
                break;
            case "TargetingScopeMessage":
                TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage(inputs[1]);
                listeners.get(playerColor).update(targetingScopeMessage);
                break;
            case "TargetingScopeSelectionMessage":
                TargetingScopeSelectionMessage targetingScopeSelectionMessage = new TargetingScopeSelectionMessage(inputs[1]);
                listeners.get(playerColor).update(targetingScopeSelectionMessage);
                break;
            case "TagbackGrenadeMessage":
                TagbackGrenadeMessage tagbackGrenadeMessage = new TagbackGrenadeMessage(inputs[1]);
                listeners.get(playerColor).update(tagbackGrenadeMessage);
                break;
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("POWERUP,"+ message.toString());
    }
}
