package Model;

import java.util.ArrayList;

public class Resources {

    private ArrayList<Weapon> availableWeapon;

    private Ammo availableAmmo;

    private Ammo allAmmo;

    private ArrayList<PowerUp> availablePowerUp;

    public Resources(){
        availableWeapon = new ArrayList<Weapon>(3);
        availableAmmo = new Ammo();
        allAmmo = new Ammo();
        availablePowerUp = new ArrayList<PowerUp>(3);
    }

    public boolean checkAmmo(int red , int blue , int yellow){

    }

    public void addAmmo(AmmoCard ammoCard){

    }

    public void addWeapon(Weapon weapon){

    }

    public void addPowerUp(PowerUp powerUp){

    }

    public void removeWeapon(Weapon weapon){

    }

    public void removePowerUp(PowerUp powerUp){

    }

    public void removeAmmo(AmmoCard ammo){

    }






}
