package model;

import model.events.ChooseActionMessage;
import model.player_package.PlayerColor;
import utils.notify.ActionNotify;

public class ActionNotifier extends ActionNotify {
    //override di tutti gli update
    public void chooseAction(PlayerColor playerColor){
        String message = "Choose an action\n";
        listeners.get(playerColor).update(new ChooseActionMessage(message));
    }
}
