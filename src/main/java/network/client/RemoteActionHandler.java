package network.client;

import model.events.*;
import model.player_package.PlayerColor;
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
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("ACTION,"+ message.toString());
    }

}
