package model.cards;

import model.Model;
import model.map_package.Square;
import model.player_package.Player;

public class Newton extends PowerUp {
    public Newton(AmmoColor color){
        cost = color;
    }

    public void usePowerUp(Player opponent, Square square){
        if(Model.runnableSquare(2, opponent.getPosition()).contains(square))
            opponent.setPosition(square);
        else
            System.out.println("Movimento troppo lungo , seleziona un' altra cella");
    }
}
