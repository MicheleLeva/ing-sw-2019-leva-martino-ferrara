package view;

import model.events.ActionEvent;
import model.player_package.PlayerColor;
import utils.observable.ActionObservable;
import utils.update.ActionUpdate;

public class ActionView extends ActionObservable implements ActionUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;

    public ActionView(PlayerColor playerColor , PlayerView view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }
    public PlayerView getView(){
        return view;
    }

    public void actionInput(){
        ActionEvent actionEvent = new ActionEvent(view , view.inputChar());
        listeners.forEach(l -> l.update(actionEvent));
    }
}
