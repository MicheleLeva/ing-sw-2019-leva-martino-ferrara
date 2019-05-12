package model.cards;

import model.Model;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

public class Newton extends PowerUp {
    public Newton(Model model , AmmoColor cost){
        super(model , cost);
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        /*if(Model.runnableSquare(2, opponent.getPosition()).contains(square))
            opponent.setPosition(square);
        else
            System.out.println("Movimento troppo lungo , seleziona un' altra cella");*/
        getModel().useNewton(playerColor);
    }
}
