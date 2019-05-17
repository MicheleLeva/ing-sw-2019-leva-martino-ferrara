package view;

import model.events.PlayerMessage;
import model.events.*;
import network.ClientConnection;
import utils.Observer;
import utils.observable.WeaponObservable;

import java.util.ArrayList;

public class RemoteWeaponView extends WeaponObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private PlayerView view;

    public RemoteWeaponView(ClientConnection clientConnection, PlayerView view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "WeaponSelectionEvent":
                WeaponSelectionEvent weaponSelectionEvent = new WeaponSelectionEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(weaponSelectionEvent));
                break;
        }
    }

    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("WEAPON," + message.toString());
    }
}
