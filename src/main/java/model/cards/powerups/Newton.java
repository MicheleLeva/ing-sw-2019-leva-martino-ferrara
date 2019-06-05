package model.cards.powerups;

import model.Model;
import model.cards.AmmoColor;
import model.player.PlayerColor;

public class Newton extends PowerUp {
    public Newton(Model model , AmmoColor cost){
        super(model , cost);
        this.name = "Newton";
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().useNewton(playerColor);
    }
}
