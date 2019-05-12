package model.player_package;

import model.Model;
import model.adrenaline_exceptions.NoPowerUpException;

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
