package model.player.action;

import controller.Checks;
import model.game.Ammo;
import model.game.Model;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.adrenaline_exceptions.NoReloadableWeaponsException;
import model.cards.weapons.Weapon;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

public class Reload extends Action {
    /**
     * Performs the reload move
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws NoReloadableWeaponsException when the player hasn't any reloadable weapons
     * @throws InsufficientAmmoException when the player hasn't enough ammo to reload the weapon
     */
    @Override
    public void perform(Model model, PlayerColor playerColor) throws NoReloadableWeaponsException,InsufficientAmmoException {

        Player currentPlayer = model.getPlayer(playerColor);
        ArrayList<Weapon> reloadableWeapon = currentPlayer.getResources().getReloadableWeapon();
        if(reloadableWeapon.isEmpty()){
            throw new NoReloadableWeaponsException();
        }

        Ammo allAmmo = currentPlayer.getResources().getAllAmmo();
        if(!Checks.canReload(reloadableWeapon,allAmmo)){
            throw new InsufficientAmmoException();
        }

        model.requestWeaponReload(playerColor);
    }
}
