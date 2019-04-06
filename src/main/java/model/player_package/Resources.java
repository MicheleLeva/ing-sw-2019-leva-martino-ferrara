package model.player_package;

import model.Ammo;
import model.cards.PowerUp;
import model.cards.Weapon;

import java.util.ArrayList;

public class Resources {
    private final Ammo allAmmo;
    private final Ammo availableAmmo;
    private final ArrayList<Weapon> weapons;
    private final ArrayList<PowerUp> powerUps;

    public Resources(Ammo allAmmo, Ammo availableAmmo, ArrayList<Weapon> weapons, ArrayList<PowerUp> powerUps){
        this.allAmmo = allAmmo;
        this.availableAmmo = availableAmmo;
        this.weapons = weapons;
        this. powerUps = powerUps;
    }



    public ArrayList<Weapon> showWeapon(){
        return weapons;
    }

    public ArrayList<PowerUp> showPowerUp(){
        return powerUps;
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
