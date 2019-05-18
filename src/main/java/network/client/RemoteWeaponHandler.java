package network.client;

import model.exchanges.events.Event;
import model.exchanges.messages.*;
import model.player.PlayerColor;
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
            case "TargetsSelectionMessage":
                TargetsSelectionMessage targetsSelectionMessage = new TargetsSelectionMessage(inputs[1], Integer.parseInt(inputs[2]));
                listeners.get(playerColor).update(targetsSelectionMessage);
                break;
            case "AskReloadMessage":
                AskReloadMessage askReloadMessage = new AskReloadMessage(inputs[1]);
                listeners.get(playerColor).update(askReloadMessage);
                break;
            case "WeaponReloadMessage":
                WeaponReloadMessage weaponReloadMessage = new WeaponReloadMessage(inputs[1]);
                listeners.get(playerColor).update(weaponReloadMessage);
                break;
            case "AskFireModesMessage":
                AskFireModesMessage askFireModesMessage = new AskFireModesMessage(inputs[1]);
                listeners.get(playerColor).update(askFireModesMessage);
                break;
            case "ChooseWeaponSquareMessage":
                ChooseWeaponSquareMessage chooseWeaponSquareMessage = new ChooseWeaponSquareMessage(inputs[1]);
                listeners.get(playerColor).update(chooseWeaponSquareMessage);
                break;
        }
    }

    @Override
    public void update(Event message) {
        c.asyncSend("WEAPON,"+ message.toString());
    }

}
