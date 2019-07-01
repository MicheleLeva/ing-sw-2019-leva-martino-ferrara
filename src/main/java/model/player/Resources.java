package model.player;

import model.game.Ammo;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;

import java.util.ArrayList;

/**
 * Representation of the player's Resources, which are: Weapons, Ammunition and PowerUps
 */
public class Resources {
    private Ammo availableAmmo;
    private final ArrayList<Weapon> weapon;
    private final ArrayList<PowerUp> powerUp;

    /**
     * Constructor for the Resources class
     */
    public Resources(){
        this.availableAmmo = new Ammo(1,1,1);
        weapon = new ArrayList<>();
        powerUp = new ArrayList<>();
    }


    /**
     * Returns a string with the name and color of the powerUps a player has
     * @return a string with the name and color of the powerUps a player has
     */
    public String showpowerUp(){
        if (powerUp.isEmpty()){
            return "No PowerUp";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < powerUp.size(); i++){
            stringBuilder.append(i+1);
            stringBuilder.append(". ");
            stringBuilder.append(powerUp.get(i).toString());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    /**
     * Removes the powerUp in the player's inventory related to the given index
     * @param index index of the powerUp in the player's inventory to remove
     * @return the powerUp in the player's inventory related to the given index
     */
    public PowerUp removePowerUp(int index){
        return powerUp.remove(index);
    }

    /**
     * Removes the chosen powerUp in the player's inventory
     * @param powerUp powerUp in the player's inventory to remove
     */
    public void removePowerUp(PowerUp powerUp){
        this.powerUp.remove(powerUp);
    }


    /**
     * Returns a list containing the reloaded weapons a player has
     * @return a list containing the reloaded weapons a player has
     */
    public ArrayList<Weapon> getReloadedWeapon(){
        ArrayList<Weapon> reloadedWeapon = new ArrayList<>();
        for (Weapon value : weapon) {
            if (value.isReloaded()) {
                reloadedWeapon.add(value);
            }
        }

        return reloadedWeapon;
    }

    /**
     * Returns a string containing the names of all the weapons a player has
     * @return a string containing the names of all the weapons a player has
     */
    public String showWeapon(){
        if (weapon.isEmpty()){
            return "No Weapon";
        }

        String result = "";

        for (int i = 0; i < weapon.size(); i++){
            int j = i+1;
            result = result + j+". " + " " + weapon.get(i).getWeaponName() +" | ";
        }

        return result;
    }

    /**
     *Returns a string containing the number of cubes of ammo a player has
     * @return a string containing the number of cubes of ammo a player has
     */
    public String showAmmo(){
        return ("Available Ammo: " +availableAmmo.toString());
    }

    /**
     * Returns player's powerups
     * @return player's powerups
     */
    public ArrayList<PowerUp> getPowerUp(){
        return powerUp;
    }

    /**
     * Adds a weapon to the player's resources
     * @param weapon to add
     */
    public void addWeapon(Weapon weapon){
        this.weapon.add(weapon);
    }

    /**
     * Adds a powerup to the player's resources
     * @param powerUp to add
     */
    public void addPowerUp(ArrayList<PowerUp> powerUp){
        this.powerUp.addAll(powerUp);
    }

    /**
     * Returns all the cubes a player has added with all the powerUps a player has
     * @return all the cubes a player has added with all the powerUps a player has
     */
    public Ammo getAllAmmo(){

        Ammo allAmmo = new Ammo(availableAmmo.getRed(),availableAmmo.getBlue(),availableAmmo.getYellow());
        for (PowerUp up : powerUp) {
            allAmmo.addAmmo(up.getAmmo());
        }
        return allAmmo;
    }

    public Ammo getAvailableAmmo() {
        return availableAmmo;
    }

    /**
     * Adds the chosen amount of ammo from the player's available ammo
     * @param red number of red cubes to add
     * @param blue number of blue cubes to add
     * @param yellow number of yellow cubes to add
     */
    public void addToAvailableAmmo(int red, int blue, int yellow){

        availableAmmo.addRed(red);
        availableAmmo.addBlue(blue);
        availableAmmo.addYellow(yellow);
    }


    /**
     * Removes the chosen amount of ammo from the player's available ammo
     * @param red number of red cubes to remove
     * @param blue number of blue cubes to remove
     * @param yellow number of yellow cubes to remove
     */
    public void removeFromAvailableAmmo(int red, int blue, int yellow){
        availableAmmo.setRed(availableAmmo.getRed()-red);
        availableAmmo.setBlue(availableAmmo.getBlue()-blue);
        availableAmmo.setYellow(availableAmmo.getYellow()-yellow);
    }


    /**
     * Returns a List containing the names of all the weapons a player can reload
     * @return a List containing the names of all the weapons a player can reload
     */
    public ArrayList<Weapon> getReloadableWeapon(){
        ArrayList<Weapon> reloadableWeapon = new ArrayList<>();
        for (Weapon value : weapon) {
            if (!value.isReloaded()) {
                reloadableWeapon.add(value);
            }
        }

        return reloadableWeapon;
    }

    /**
     * Returns a List containing  all the weapons a player has
     * @return Returns a List containing  all the weapons a player has
     */
    public ArrayList<Weapon> getAllWeapon(){return this.weapon;}

    /**
     * Returns a string containing the names of all the weapons a player can reload
     * @return a string containing the names of all the weapons a player can reload
     */
    public String showReloadableWeapon(){
        ArrayList<Weapon> reloadableWeapons = getReloadableWeapon();
        Ammo allAmmo = getAllAmmo();
        ArrayList<Weapon> reloadableWeaponsCopy = new ArrayList<>(reloadableWeapons);
        String result = "";

        for(Weapon weapon : reloadableWeapons){
            Ammo cost = weapon.getBaseCost();
            if(cost.getRed()>allAmmo.getRed() || cost.getBlue()>allAmmo.getBlue() || cost.getYellow()>allAmmo.getYellow()){
                reloadableWeaponsCopy.remove(weapon);
            }
        }

        for (int i = 0; i < reloadableWeaponsCopy.size(); i++){
            Weapon currentWeapon = reloadableWeaponsCopy.get(i);
            result = result +(i + 1) +". " +currentWeapon.getWeaponName() +" Reload Cost: " +currentWeapon.getBaseCost() +".\n";
        }
        return result;

    }

    /**
     * Returns a string containing the names of all the unloaded weapons a player has
     * @return a string containing the names of all the unloaded weapons a player has
     */
    public String printUnloadedWeapons(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getReloadableWeapon().size(); i++){
            stringBuilder.append(getReloadableWeapon().get(i).getWeaponName());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    /**
     * Checks whether the player has at least one cube
     * @return true if the player has at least one cube, false otherwise
     */
    public boolean hasOneCube(){
        return availableAmmo.getRed() > 0 || availableAmmo.getBlue() > 0 || availableAmmo.getYellow() > 0;
    }
}
