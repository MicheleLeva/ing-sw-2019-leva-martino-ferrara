package model;

import model.events.RunMessage;
import utils.notify.GameNotify;

public class GameNotifier extends GameNotify {
    public void notifyRun(String playerName , String newSquare){
        String message;
        message = playerName +" just moved to " +newSquare +".\n";
        listeners.forEach(l -> l.update(new RunMessage(message)));
    }
}
