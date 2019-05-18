package view;

import model.exchanges.events.ActionEvent;
import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.events.Event;
import model.player.PlayerColor;
import utils.Observable;
import utils.update.ActionUpdate;

public class ActionView extends Observable<Event> implements ActionUpdate {
    private final PlayerColor playerColor;
    private final View view;

    public ActionView(PlayerColor playerColor , View view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }
    public View getView(){
        return view;
    }

    @Override
    public void update(ChooseActionMessage chooseActionMessage){
        view.printMessage(chooseActionMessage.getMessage());

        Event actionEvent = new ActionEvent(view , view.inputChar());
        this.notify(actionEvent);
    }
}
