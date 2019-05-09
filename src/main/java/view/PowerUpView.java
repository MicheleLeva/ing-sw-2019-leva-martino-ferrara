package view;

import model.player_package.PlayerColor;
import utils.observable.PowerUpObservable;
import utils.update.PowerUpUpdate;

public class PowerUpView extends PowerUpObservable implements PowerUpUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;

    public PowerUpView(PlayerColor playerColor , PlayerView view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public PlayerView getView(){
        return view;
    }
}
