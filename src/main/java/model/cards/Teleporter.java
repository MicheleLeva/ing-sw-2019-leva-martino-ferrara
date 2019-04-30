package model.cards;

import model.map_package.Square;
import model.player_package.Player;

public class Teleporter extends PowerUp {
    public Teleporter(AmmoColor color){
        color = color;
    }

    public void usePowerUp(Player player, Square square){
        player.setPosition(square);

    }
}
