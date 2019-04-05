package model.player_package;

import model.Ammo;
import model.Cards.PowerUp;
import model.Cards.Weapon;

import java.util.ArrayList;

public class Resources {
    private Ammo allAmmo;
    private Ammo availableAmmo;

    public ArrayList<Weapon> showWeapon(){
        return null;
    }

    public ArrayList<PowerUp> showPowerUp(){
        return null;
    }

    public Ammo getAllAmmo(){
        return allAmmo;
    }

    public Ammo getAvailableAmmo() {
        return availableAmmo;
    }

    public void addAmmo(int red, int blue, int yellow){

    }

    public void addAvailableAmmo(int red, int blue, int yellow){

    }

    public void removeFromAllAmmo(int red, int blue, int yellow){

    }

    public void removeFromAvailableAmmo(int red, int blue, int yellow){

    }
}
