package model.cards;

import model.player_package.Player;
import model.player_package.PlayerColor;

public abstract class PowerUp extends Card{

    protected AmmoColor cost;
    protected String powerUpName;

    public AmmoColor getCost(){
        return cost;
    }

    public void usePowerUp(PlayerColor playerColor){

    }
    public AmmoColor getCost(){
        return cost;
    }

    public String getPowerUpName(){
        return powerUpName;
    }

}
