package model.player.action;

import model.Model;
import model.adrenaline_exceptions.NoReloadableWeaponsException;
import model.player.Player;
import model.player.PlayerColor;

public class Shoot extends Action {


    //todo implement method
    @Override
    public void perform (Model model, PlayerColor playerColor) throws NoReloadableWeaponsException {
        Player currentPlayer = model.getPlayer(playerColor);
        //check if he can t shoot
        if(currentPlayer.getResources().getReloadedWeapon().isEmpty())
            throw new NoReloadableWeaponsException();
        model.showWeaponCards(playerColor);
    }
}
