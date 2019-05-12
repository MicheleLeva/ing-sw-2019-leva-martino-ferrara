package model.player_package;

import model.Ammo;
import model.cards.PowerUp;
import model.cards.Weapon;

import java.util.ArrayList;

public class Resources {
    private final Ammo allAmmo;
    private final Ammo availableAmmo;
    private final ArrayList<Weapon> weapon;
    private final ArrayList<PowerUp> powerUp;

    public Resources(){
        this.allAmmo = new Ammo(2,2,2);
        this.availableAmmo = new Ammo(1,1,1);
        weapon = new ArrayList<>();
        powerUp = new ArrayList<>();
    }


    public String showpowerUp(){
        if (powerUp.isEmpty()){
            return "No PowerUp";
        }

        String result = "";

        for (int i = 0; i < powerUp.size(); i++){
            result = result + powerUp.get(i).toString() +" | ";
        }

        return result;
    }

    public PowerUp removePowerUp(int index){
        return powerUp.remove(index);
    }

    public ArrayList<Weapon> getReloadableWeapon(){
        ArrayList<Weapon> reloadableWeapon = new ArrayList<>();
        for (int i = 0; i< weapon.size(); i++){
            if (!weapon.get(i).isReloaded()){
                reloadableWeapon.add(weapon.get(i));
            }
        }

        return reloadableWeapon;
    }

    public String showWeapon(){
        if (weapon.isEmpty()){
            return "No Weapon";
        }

        String result = "";

        for (int i = 0; i < weapon.size(); i++){
            result = result + weapon.get(i).toString() +" | ";
        }

        return result;
    }

    public ArrayList<Weapon> showWeapons(){
        ArrayList<Weapon> weapons = new ArrayList<>(weapon);
        return weapons;
    }

    public ArrayList<PowerUp> showPowerUps(){
        ArrayList<PowerUp> powerUps = new ArrayList<>(powerUp);
        return powerUps;
    }

    public String showAmmo(){
        return ("Available Ammo:\n" +availableAmmo.toString());
    }

    public ArrayList<PowerUp> getPowerUp(){
        return powerUp;
    }

    public void addWeapon(Weapon weapon){
        this.weapon.add(weapon);
    }

    public void addPowerUp(ArrayList<PowerUp> powerUp){
        this.powerUp.addAll(powerUp);
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

    public void addAmmo(Ammo ammo){
        allAmmo.remove(ammo);
        availableAmmo.add(ammo);
    }
}
