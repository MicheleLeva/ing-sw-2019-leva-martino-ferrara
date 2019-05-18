package view;

import model.exchanges.events.Event;
import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.NameSetMessage;
import model.exchanges.messages.RunMessage;
import model.exchanges.messages.ShootMessage;
import model.player.PlayerColor;
import utils.Observable;
import utils.update.GameUpdate;

public class GameView extends Observable<Event> implements GameUpdate {
    private final PlayerColor playerColor;
    private final View view;
    public GameView(PlayerColor playerColor , View view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    @Override
    public void update(NameSetMessage nameSetMessage){
        view.printMessage(nameSetMessage.getMessage());
        // il giocatore inserisce il nome che viene dato al translator per inoltrarlo
        //notify NameSetEvent
    }

    @Override
    public void update(RunMessage runMessage){
        System.out.println(runMessage.getMessage());
    }

    @Override
    public void update(GenericMessage genericMessage){
        view.printMessage(genericMessage.getMessage());
    }

    @Override
    public void update(ShootMessage shootMessage) {
        System.out.println(shootMessage.getMessage());
    }
}
