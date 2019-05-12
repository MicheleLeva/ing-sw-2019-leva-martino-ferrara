package view;

import model.events.GenericMessage;
import model.events.NameSetMessage;
import model.events.RunMessage;
import model.events.ShootMessage;
import model.player_package.PlayerColor;
import utils.update.GameUpdate;

public class GameView implements GameUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;
    public GameView(PlayerColor playerColor , PlayerView view){
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
