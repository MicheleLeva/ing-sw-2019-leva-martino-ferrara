package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.*;
import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.PowerUpNotify;

/**
 * Handler for the "PowerUp" Message and Event objects.
 * It works both as a Controller and as a Model for the View.
 */
public class RemotePowerUpHandler extends PowerUpNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemotePowerUpHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

    /**
     * It simulates the Model notify method.
     * Once received the message object form the NetworkHandler the method uses the correct update in the view through Overloading.
     * @param inputs array of strings received from the NetworkHandler.
     */
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

    /**
     * Received an Event object from the View it serializes it adding its class tag then sends the string through the socket.
     * @param message to be sent.
     */
    @Override
    public void update(Event message) {
        c.asyncSend("POWERUP,"+ message.toString());
    }
}
