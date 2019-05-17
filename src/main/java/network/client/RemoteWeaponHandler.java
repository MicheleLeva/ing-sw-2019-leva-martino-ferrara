package network.client;

import model.events.*;
import model.player_package.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.notify.WeaponNotify;

public class RemoteWeaponHandler extends WeaponNotify implements Observer<Event> {

    private ClientConnection c;
    private PlayerColor playerColor;

    public RemoteWeaponHandler(ClientConnection clientConnection, PlayerColor playerColor){
        this.c = clientConnection;
        this.playerColor = playerColor;
    }

    public void stringToMessage(String[] inputs){
        switch(inputs[0]) {
            case "ShowWeaponCardsMessage":
                ShowWeaponCardsMessage showWeaponCardsMessage = new ShowWeaponCardsMessage(inputs[1]);
                listeners.get(playerColor).update(showWeaponCardsMessage);
                break;
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("WEAPON,"+ message.toString());
    }

}
