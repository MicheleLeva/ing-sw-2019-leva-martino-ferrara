package Model;

import java.util.ArrayList;

public class Resources {

    private ArrayList<WeaponCard> availableWeapon;

    private Ammo availableAmmo;

    private Ammo allAmmo;

    private ArrayList<PowerUpCard> availablePowerUp;

    public Resources(){
        availableWeapon = new ArrayList<WeaponCard>(3);
        availableAmmo = new Ammo();
        allAmmo = new Ammo();
        availablePowerUp = new ArrayList<PowerUpCard>(3);
    }

    public boolean checkAmmo(int red , int blue , int yellow){
        return true;
    }

    public void addAmmo(AmmoCard ammoCard){

    }

    public void addWeapon(WeaponCard weapon){

    }

    public void addPowerUp(PowerUpCard powerUp){

    }

    public void removeWeapon(WeaponCard weapon){

    }

    public void removePowerUp(PowerUpCard powerUp){

    }

    public void removeAmmo(AmmoCard ammo){

    }






}
