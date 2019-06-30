package model;

import model.cards.AmmoColor;

import java.util.HashMap;

/**
 * Representation of a player's ammunition cubes
 */
public class Ammo {

    private HashMap<AmmoColor, Integer> ammo ;

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

/*    public Ammo(Ammo ammo){
            addRed(ammo.getRed());
            addBlue(ammo.getBlue());
            addYellow(ammo.getYellow());
    }*/

    /**
     * @return The number of red cubes
     */
    public int getRed(){
        return ammo.get(AmmoColor.RED);
    }

    /**
     * @return The number of yellow cubes
     */
    public int getYellow(){
        return ammo.get(AmmoColor.YELLOW);
    }

    /**
     * @return The number of blue cubes
     */
    public int getBlue(){
        return ammo.get(AmmoColor.BLUE);
    }

    /**
     * Sets the number of red cubes
     */
    public void setRed(int red){
        ammo.put(AmmoColor.RED, red);
    }

    /**
     * Sets the number of blue cubes
     */
    public void setBlue(int blue){
        ammo.put(AmmoColor.BLUE, blue);

    }

    /**
     * Sets the number of yellow cubes
     */
    public void setYellow(int yellow){
        ammo.put(AmmoColor.YELLOW, yellow);
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
     *
     * @param ammoColor
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
     */
    public boolean isEnough(Ammo ammo){
        return (getRed() >= ammo.getRed() && getBlue() >= ammo.getBlue() && getYellow() >= ammo.getYellow());
    }

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

/*    public void add(Ammo ammo){
        setRed(getRed() + ammo.getRed());
        setBlue(getBlue() + ammo.getBlue());
        setYellow(getYellow() + ammo.getYellow());
    }*/

    public void remove(Ammo ammo){
        setRed(getRed() - ammo.getRed());
        setBlue(getBlue() - ammo.getBlue());
        setYellow(getYellow() - ammo.getYellow());
    }
}
