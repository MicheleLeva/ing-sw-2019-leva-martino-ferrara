package model.player.action;

import model.Model;
import model.adrenaline_exceptions.NoPowerUpException;
import model.player.Player;
import model.player.PlayerColor;

public class UsePowerUp extends Action {
    /**
     * performs the use powerup move
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws NoPowerUpException when the player has no powerUp cards
     */
    @Override
    public void perform(Model model , PlayerColor playerColor) throws NoPowerUpException {
        Player player = model.getPlayer(playerColor);

        if(player.getResources().getPowerUp().isEmpty()){
            throw new NoPowerUpException();
        }

        else{
            model.choosePowerUp(playerColor);
        }
    }
}
