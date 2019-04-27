package model.cards;

import model.Model;
import model.map_package.Square;
import model.player_package.Player;

public class Newton extends PowerUp {
    public Newton(AmmoColor color){
        powerUpName = "Newton";
        cost = color;
    }

    public void usePowerUp(){

        //notify(new NewtonMessage());
    }
}
