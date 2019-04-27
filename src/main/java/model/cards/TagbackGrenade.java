package model.cards;

import model.map_package.Square;
import model.player_package.Player;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(AmmoColor color){
        powerUpName = "TagbackGrenade";
        cost = color;
    }

    public void usePowerUp(Player player, Square square){
        player.setPosition(square);

    }
}
