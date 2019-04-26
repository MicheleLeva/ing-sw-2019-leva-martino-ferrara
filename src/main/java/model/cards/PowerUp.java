package model.cards;

import model.player_package.Player;

public abstract class PowerUp extends Card{

    protected AmmoColor cost;

    public AmmoColor getCost(){
        return cost;
    }

    public void usePowerUp(){

    }

}
