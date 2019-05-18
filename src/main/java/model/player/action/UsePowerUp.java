package model.player.action;

import model.Model;
import model.adrenaline_exceptions.NoPowerUpException;
import model.player.Player;
import model.player.PlayerColor;

public class UsePowerUp extends Action {
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