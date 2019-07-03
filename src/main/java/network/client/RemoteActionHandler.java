package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.SetAfkMessage;
import model.exchanges.messages.VoteMapMessage;
import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.ActionNotify;

/**
 * Handler for the "Action" message and event objects.
 * It works both as a Controller and as a Model for the View.
 * @author Michele Leva
 */
public class RemoteActionHandler extends ActionNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemoteActionHandler(ClientConnection clientConnection, PlayerColor playerColor){
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
            case "ChooseActionMessage":
                ChooseActionMessage chooseActionMessage = new ChooseActionMessage(inputs[1]);
                listeners.get(playerColor).update(chooseActionMessage);
                break;
            case "SetAfkMessage":
                SetAfkMessage setAfkMessage = new SetAfkMessage(inputs[1]);
                listeners.get(playerColor).update(setAfkMessage);
                break;
            case "VoteMapMessage":
                VoteMapMessage voteMapMessage = new VoteMapMessage(inputs[1]);
                listeners.get(playerColor).update(voteMapMessage);
                break;
            default:
                System.out.println("A message is in the wrong handler:\n" + inputs[0] + ": " + inputs[1]);
        }
    }

    /**
     * Received an Event object from the View it serializes it adding its class tag then sends the string through the socket.
     * @param message to be sent.
     */
    @Override
    public void update(Event message) {
        c.asyncSend("ACTION,"+ message.toString());
    }

}
