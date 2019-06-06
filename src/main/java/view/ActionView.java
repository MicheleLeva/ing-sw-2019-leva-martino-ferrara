package view;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.events.Event;
import model.exchanges.messages.SetAfkMessage;
import model.exchanges.messages.VoteMapMessage;
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

    @Override
    public void update(SetAfkMessage setAfkMessage){
        view.printMessage(setAfkMessage.getMessage());
        view.getScanner().nextLine();
        System.out.println();
        Event event = new QuitAfkEvent(view, view.inputChar());
        this.notify(event);
    }

    @Override
    public void update(VoteMapMessage voteMapMessage){
        view.printMessage(voteMapMessage.getMessage());

        Event event = new VoteMapEvent(view, view.inputChar());
        this.notify(event);
    }
}
