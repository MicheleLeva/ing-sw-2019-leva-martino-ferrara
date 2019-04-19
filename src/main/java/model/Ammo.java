package model;

import model.cards.AmmoColor;

import java.util.HashMap;

public class Ammo {

    private HashMap<AmmoColor, Integer> ammo;


    public Ammo(int red, int blue, int yellow){
        ammo = new HashMap<>();
        ammo.put(AmmoColor.RED, 0);
        ammo.put(AmmoColor.BLUE, 0);
        ammo.put(AmmoColor.YELLOW, 0);
        setRed(red);
        setBlue(blue);
        setYellow(yellow);

    }

    public int getRed(){
        return ammo.get(AmmoColor.RED);
    }

    public int getYellow(){
        return ammo.get(AmmoColor.YELLOW);
    }

    public int getBlue(){
        return ammo.get(AmmoColor.BLUE);
    }

    public void setRed(int number){
        ammo.put(AmmoColor.RED, ammo.get(AmmoColor.RED) + number);
    }

    public void setBlue(int number){
        ammo.put(AmmoColor.BLUE, ammo.get(AmmoColor.BLUE) + number);

    }

    public void setYellow(int number){
        ammo.put(AmmoColor.YELLOW, ammo.get(AmmoColor.YELLOW) + number);
    }



}
