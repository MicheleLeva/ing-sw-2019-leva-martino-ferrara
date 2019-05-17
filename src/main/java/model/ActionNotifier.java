package model;

import model.events.*;
import model.player_package.PlayerColor;
import utils.update.ViewObservable;

public class ActionNotifier extends ViewObservable<PlayerMessage> {
    //override di tutti gli update
    public void chooseAction(PlayerColor playerColor , String availableAction){
        String message = "Choose an action\n";
        message = message +availableAction +".\n";
        notify(new ChooseActionMessage(message), playerColor);

        //listeners.get(playerColor).update(new ChooseActionMessage(message));
    }
}

