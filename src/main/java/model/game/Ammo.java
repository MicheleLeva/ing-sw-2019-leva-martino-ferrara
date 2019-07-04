package model.game;

import model.cards.AmmoColor;

import java.util.HashMap;

/**
 * Representation of a player's ammunition cubes
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class Ammo {

    private final HashMap<AmmoColor, Integer> ammo ;

    /**
     * Constructor for the Ammo class
     * @param red number of red cubes
     * @param blue number of blue cubes
     * @param yellow number of yellow cubes
     */
    public Ammo(int red, int blue, int yellow){
        ammo = new HashMap<>();
        ammo.put(AmmoColor.RED,red);
        setBlue(blue);
        setYellow(yellow);
    }

    /**
     * Returns the number of red cubes
     * @return The number of red cubes
     */
    public int getRed(){
        return ammo.get(AmmoColor.RED);
    }

    /**
     * Returns the number of yellow cubes
     * @return The number of yellow cubes
     */
    public int getYellow(){
        return ammo.get(AmmoColor.YELLOW);
    }

    /**
     * Returns the number of blue cubes
     * @return The number of blue cubes
     */
    public int getBlue(){
        return ammo.get(AmmoColor.BLUE);
    }

    /**
     * Sets the number of red cubes
     * @param red number of yellow cubes to set in the ammo
     */
    public void setRed(int red){
        ammo.put(AmmoColor.RED, red);
        if(ammo.get(AmmoColor.RED)<0)
            ammo.put(AmmoColor.RED,0);
    }

    /**
     * Sets the number of blue cubes
     * @param blue number of yellow cubes to set in the ammo
     */
    public void setBlue(int blue){
        ammo.put(AmmoColor.BLUE, blue);
        if(ammo.get(AmmoColor.BLUE)<0)
            ammo.put(AmmoColor.BLUE,0);

    }

    /**
     * Sets the number of yellow cubes
     * @param yellow number of yellow cubes to set in the ammo
     */
    public void setYellow(int yellow){
        ammo.put(AmmoColor.YELLOW, yellow);
        if(ammo.get(AmmoColor.YELLOW)<0)
            ammo.put(AmmoColor.YELLOW,0);
    }


    /**
     * Adds the given number of red cubes
     * @param num The number of red cubes
     */
    public void addRed(int num){
        int number = num + ammo.get(AmmoColor.RED);
        setRed(number);
    }

    /**
     * Adds the given number of blue cubes
     * @param num The number of blue cubes
     */
    public void addBlue(int num){
        int number = num + ammo.get(AmmoColor.BLUE);
        setBlue( number);
    }

    /**
     * Adds the given number of yellow cubes
     * @param num The number of yellow cubes
     */
    public void addYellow(int num){
        int number = num + ammo.get(AmmoColor.YELLOW);
        setYellow(number);
    }

    /**
     * Adds ammo of the given colors
     * @param ammoColor to add
     */
    public void addAmmo(AmmoColor ammoColor){
        switch(ammoColor){
            case RED:
                addRed(1);
                break;
            case BLUE:
                addBlue(1);
                break;
            case YELLOW:
                addYellow(1);
                break;
        }
    }

    /**
     * Check if the ammo the player holds is greater than the given ammo
     * @param ammo given ammo to confront with the layer's ammo
     * @return true if the ammo the player holds is greater than the given ammo, false otherwise
     */
    public boolean isEnough(Ammo ammo){
        return (getRed() >= ammo.getRed() && getBlue() >= ammo.getBlue() && getYellow() >= ammo.getYellow());
    }

    /**
     * Represents the ammo class on the CLI.
     * @return a string representing the ammo class on the CLI.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        String blue = CLI.getBlue() +getBlue();
        String red = CLI.getRed() +getRed();
        String yellow = CLI.getYellow() +getYellow();

        stringBuilder.append(blue);
        stringBuilder.append(CLI.getResetString());
        stringBuilder.append(" ");
        stringBuilder.append(red);
        stringBuilder.append(CLI.getResetString());
        stringBuilder.append(" ");
        stringBuilder.append(yellow);
        stringBuilder.append(CLI.getResetString());

        return stringBuilder.toString();


    }

    /**
     * Removes the input ammo from the ammo
     * @param ammo the quantity to remove
     */
    public void remove(Ammo ammo){
        setRed(getRed() - ammo.getRed());
        setBlue(getBlue() - ammo.getBlue());
        setYellow(getYellow() - ammo.getYellow());

    }
}
