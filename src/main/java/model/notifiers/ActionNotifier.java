package model.notifiers;

import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.PlayerMessage;
import model.player.PlayerColor;
import utils.ViewObservable;

public class ActionNotifier extends ViewObservable<PlayerMessage> {

    public void chooseAction(PlayerColor playerColor , String availableAction){
        String message = "Choose an action\n";
        message = message +availableAction +".\n";
        notify(new ChooseActionMessage(message), playerColor);
    }
}

