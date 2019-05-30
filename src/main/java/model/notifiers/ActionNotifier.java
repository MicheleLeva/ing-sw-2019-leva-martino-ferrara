package model.notifiers;

import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.PlayerMessage;
import model.exchanges.messages.SetAfkMessage;
import model.player.PlayerColor;
import utils.ViewObservable;

public class ActionNotifier extends ViewObservable<PlayerMessage> {

    public void chooseAction(PlayerColor playerColor , String availableAction){
        String message = "Choose an action\n";
        message = message +availableAction +".\n";
        PlayerMessage playerMessage = new ChooseActionMessage(message);
        notify(playerMessage, playerColor);
    }

    public void setPlayerAfk(PlayerColor playerColor){
        String message = "You're afk. If you want to go back to the game insert a character and press enter";
        String toOthers = playerColor.toString() + "is afk. Their turn will be skipped";
        PlayerMessage playerMessage = new SetAfkMessage(message);
        PlayerMessage messageToOthers = new GenericMessage(toOthers);
        notify(playerMessage, playerColor);
        notifyOthers(messageToOthers, playerColor);
    }
}

