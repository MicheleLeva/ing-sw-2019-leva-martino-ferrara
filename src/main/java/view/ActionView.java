package view;

import model.events.ActionEvent;
import model.events.ChooseActionMessage;
import model.events.Event;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.observable.ActionObservable;
import utils.update.ActionUpdate;

public class ActionView extends Observable<Event> implements ActionUpdate {
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

    @Override
    public void update(ChooseActionMessage chooseActionMessage){
        view.printMessage(chooseActionMessage.getMessage());

        Event actionEvent = new ActionEvent(view , view.inputChar());
        this.notify(actionEvent);
        //listeners.forEach(l -> l.update(actionEvent));
    }
}
