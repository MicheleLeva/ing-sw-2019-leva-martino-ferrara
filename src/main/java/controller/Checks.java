package controller;

import model.Ammo;
import model.cards.Card;
import model.player_package.Player;

public class Checks {

    public static boolean enoughAmmo(Card card, Ammo ammo){
        return true;
    }

    public static boolean hasMaxWeapon(Player player){
        return true;
    }

    public static boolean hasMaxAmmo(Player player){
        return true;
    }

    public static boolean hasMaxPowerUp(Player player){
        return true;
    }
}
