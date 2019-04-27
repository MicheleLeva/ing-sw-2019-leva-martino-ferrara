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

    public Resources(){
        this.allAmmo = new Ammo(2,2,2);
        this.availableAmmo = new Ammo(1,1,1);
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
    }



    public ArrayList<Weapon> showWeapons(){
        return weapons;
    }

    public ArrayList<PowerUp> showPowerUps(){
        return powerUps;
    }

    public void addWeapon(Weapon weapon){
        this.weapons.add(weapon);
    }

    public void addPowerUp(PowerUp powerUp){
        this.powerUps.add(powerUp);
    }

    public Ammo getAllAmmo(){
        return allAmmo;
    }

    public Ammo getAvailableAmmo() {
        return availableAmmo;
    }

    public void addToAllAmmo(int red, int blue, int yellow){

        allAmmo.setRed(red);
        allAmmo.setBlue(blue);
        allAmmo.setYellow(yellow);

    }

    public void addToAvailableAmmo(int red, int blue, int yellow){

        availableAmmo.setRed(red);
        availableAmmo.setBlue(blue);
        availableAmmo.setYellow(yellow);
    }

    public void removeFromAllAmmo(int red, int blue, int yellow){
        allAmmo.setRed(-red);
        allAmmo.setBlue(-blue);
        allAmmo.setYellow(-yellow);

    }

    public void removeFromAvailableAmmo(int red, int blue, int yellow){
        availableAmmo.setRed(-red);
        availableAmmo.setBlue(-blue);
        availableAmmo.setYellow(-yellow);
    }

    public void removeFromAvailableAmmo(Ammo ammo){
        availableAmmo.setRed(-ammo.getRed());
        availableAmmo.setBlue(-ammo.getBlue());
        availableAmmo.setYellow(-ammo.getYellow());
    }
}
