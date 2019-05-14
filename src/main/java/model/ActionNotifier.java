package model;

import model.events.ChooseActionMessage;
import model.player_package.PlayerColor;
import utils.notify.ActionNotify;

public class ActionNotifier extends ActionNotify {
    //override di tutti gli update
    public void chooseAction(PlayerColor playerColor , String availableAction){
        String message = "Choose an action\n";
        message = message +availableAction +".\n";
        listeners.get(playerColor).update(new ChooseActionMessage(message));
    }
}
