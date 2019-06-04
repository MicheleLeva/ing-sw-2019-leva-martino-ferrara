package model.player;

import model.Ammo;
import model.cards.AmmoColor;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;

import java.util.ArrayList;

public class Resources {
    private Ammo availableAmmo;
    private final ArrayList<Weapon> weapon;
    private final ArrayList<PowerUp> powerUp;
    private final ArrayList<Weapon> reloadableWeapon;

    public Resources(){
        this.availableAmmo = new Ammo(1,1,1);
        weapon = new ArrayList<>();
        powerUp = new ArrayList<>();
        reloadableWeapon = new ArrayList<>();
        //todo rivelere reloadable
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

    public void removePowerUp(PowerUp powerUp){
        this.powerUp.remove(powerUp);
    }


    public ArrayList<Weapon> getReloadedWeapon(){
        ArrayList<Weapon> reloadedWeapon = new ArrayList<>();
        for (int i = 0; i< weapon.size(); i++){
            if (weapon.get(i).isReloaded()){
                reloadedWeapon.add(weapon.get(i));
            }
        }

        return reloadedWeapon;
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

        System.out.println("availableammo"+availableAmmo);
        Ammo allAmmo = new Ammo(availableAmmo.getRed(),availableAmmo.getBlue(),availableAmmo.getYellow());
        for (int i = 0; i < powerUp.size(); i++){
            System.out.println("powerupcost"+powerUp.get(i).getAmmo());
            allAmmo.addAmmo(powerUp.get(i).getAmmo());
        }
        System.out.println("allammo"+allAmmo);

        return allAmmo;
    }

    public Ammo getAvailableAmmo() {
        return availableAmmo;
    }

    public void addToAvailableAmmo(int red, int blue, int yellow){

        availableAmmo.setRed(red);
        availableAmmo.setBlue(blue);
        availableAmmo.setYellow(yellow);
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

    public void addAmmo(Ammo ammoColor){


    }

    public boolean enoughAmmo(Ammo ammo){
        int tempRed = availableAmmo.getRed();
        int tempBlue = availableAmmo.getBlue();
        int tempYellow = availableAmmo.getYellow();
        Ammo tempAmmo = new Ammo(tempRed,tempBlue,tempYellow);
        if(ammo.getRed()-tempRed>=0 &&
            ammo.getBlue()-tempBlue>=0 &&
            ammo.getYellow()-tempYellow>=0)
            return true;
        else
            return false;
    }

    public ArrayList<Weapon> getReloadableWeapon(){
        return reloadableWeapon;
    }

    public String showReloadableWeapon(){
        String result = "";
        for (int i = 0; i < reloadableWeapon.size(); i++){
            Weapon currentWeapon = reloadableWeapon.get(i);
            result = result +(i + 1) +". " +currentWeapon.toString() +" Reload Cost: " +currentWeapon.getBaseCost().toString() +".\n";
        }
        return result;
    }

    public String showPowerUp(){
        String result = "";
        for (int i = 0; i < powerUp.size(); i++){
            PowerUp currentPowerUp = powerUp.get(i);
            result = result +currentPowerUp.toString() +" Ammo: " +currentPowerUp.getAmmo().toString() +".\n";
        }
        return result;
    }

    public String printUnloadedWeapons(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < reloadableWeapon.size(); i++){
            stringBuilder.append(reloadableWeapon.get(i).toString());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}
