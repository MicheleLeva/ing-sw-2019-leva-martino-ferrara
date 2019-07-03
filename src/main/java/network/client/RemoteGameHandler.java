package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.RunMessage;
import model.exchanges.messages.ShootMessage;
import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.GameNotify;

/**
 * Handler for the "Game" Message and Event objects.
 * It works both as a Controller and as a Model for the View.
 * @author Michele Leva
 */
public class RemoteGameHandler extends GameNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemoteGameHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

    /**
     * It simulates the Model notify method.
     * Once received the message object form the handler the method uses the correct update in the view through Overloading.
     * @param inputs array of strings received from the NetworkHandler.
     */
    public void stringToMessage(String[] inputs){
        switch(inputs[0]) {
            case "RunMessage":
                RunMessage runMessage = new RunMessage(inputs[1]);
                listeners.get(playerColor).update(runMessage);
                break;
            case "GenericMessage":
                GenericMessage genericMessage = new GenericMessage(inputs[1]);
                listeners.get(playerColor).update(genericMessage);
                break;
            case "ShootMessage":
                ShootMessage shootMessage = new ShootMessage(inputs[1]);
                listeners.get(playerColor).update(shootMessage);
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
        c.asyncSend("GAME,"+ message.toString());
    }
}
