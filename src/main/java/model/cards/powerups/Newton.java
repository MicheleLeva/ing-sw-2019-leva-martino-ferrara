package model.cards.powerups;

import model.game.Model;
import model.cards.AmmoColor;
import model.player.PlayerColor;

/**
 * Class of the Newton Power Up
 */
public class Newton extends PowerUp {

    /**
     * Constructor for the Newton class
     * @param model game's model
     * @param cost color of the power up
     */
    public Newton(Model model , AmmoColor cost){
        super(model , cost);
        this.name = "Newton";
    }

    /**
     * Call the model method that uses the Power Up
     * @param playerColor of the target
     */
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().getCurrent().setSelectedNewton(this);
        getModel().useNewton(playerColor);

    }
}
