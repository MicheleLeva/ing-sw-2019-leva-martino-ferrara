package network.client;

import model.events.*;
import model.player_package.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.GameNotify;

public class RemoteGameHandler extends GameNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemoteGameHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

    public void stringToMessage(String[] inputs){
        switch(inputs[0]) {
            case "NameSetMessage":
                NameSetMessage nameSetMessage = new NameSetMessage(inputs[1]);
                listeners.get(playerColor).update(nameSetMessage);
                break;
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
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("GAME,"+ message.toString());
    }
}