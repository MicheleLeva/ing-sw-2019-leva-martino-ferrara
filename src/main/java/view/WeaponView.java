package view;

import model.player_package.PlayerColor;
import utils.observable.WeaponObservable;
import utils.update.WeaponUpdate;

public class WeaponView extends WeaponObservable implements WeaponUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;

    public WeaponView(PlayerColor playerColor , PlayerView view){
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
