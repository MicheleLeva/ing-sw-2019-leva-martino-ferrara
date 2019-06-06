package model.player.action;

import controller.Checks;
import model.Ammo;
import model.Model;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.adrenaline_exceptions.NoReloadableWeaponsException;
import model.cards.weapons.Weapon;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

public class Reload extends Action {
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
