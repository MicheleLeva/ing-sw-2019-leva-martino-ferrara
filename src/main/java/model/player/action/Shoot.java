package model.player.action;

import model.game.Model;
import model.adrenaline_exceptions.NoReloadedWeaponsExceptions;
import model.player.Player;
import model.player.PlayerColor;

public class Shoot extends Action {

    /**
     * Performs the shoot move
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws NoReloadedWeaponsExceptions exception thrown if the player doesn't have loaded weapons
     */
    @Override
    public void perform (Model model, PlayerColor playerColor) throws NoReloadedWeaponsExceptions {
        Player currentPlayer = model.getPlayer(playerColor);
        //check if he can shoot
        if(currentPlayer.getResources().getReloadedWeapon().isEmpty())
            throw new NoReloadedWeaponsExceptions();
        model.showWeaponCards(playerColor);
    }
}
