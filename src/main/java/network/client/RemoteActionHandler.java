package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.SetAfkMessage;
import model.exchanges.messages.VoteMapMessage;
import model.player.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.ActionNotify;

public class RemoteActionHandler extends ActionNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemoteActionHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

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

    @Override
    public void update(Event message) {
        c.asyncSend("ACTION,"+ message.toString());
    }

}
