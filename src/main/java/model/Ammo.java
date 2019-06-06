package model;

import model.cards.AmmoColor;

import java.util.HashMap;

public class Ammo {

    private HashMap<AmmoColor, Integer> ammo ;


    public Ammo(int red, int blue, int yellow){
        ammo = new HashMap<>();
        ammo.put(AmmoColor.RED,red);
        setBlue(blue);
        setYellow(yellow);
    }

    public Ammo(Ammo ammo){
            addRed(ammo.getRed());
            addBlue(ammo.getBlue());
            addYellow(ammo.getYellow());
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

    public void setRed(int red){
        ammo.put(AmmoColor.RED, red);
    }

    public void setBlue(int blue){
        ammo.put(AmmoColor.BLUE, blue);

    }

    public void setYellow(int yellow){
        ammo.put(AmmoColor.YELLOW, yellow);
    }


    public void addRed(int num){
        int number = num + ammo.get(AmmoColor.RED);
        setRed(number);
    }

    public void addBlue(int num){
        int number = num + ammo.get(AmmoColor.BLUE);
        setBlue( number);
    }

    public void addYellow(int num){
        int number = num + ammo.get(AmmoColor.BLUE);
        setYellow(number);
    }

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

    public void add(Ammo ammo){
        setRed(getRed() + ammo.getRed());
        setBlue(getBlue() + ammo.getBlue());
        setYellow(getYellow() + ammo.getYellow());
    }

    public void remove(Ammo ammo){
        setRed(getRed() - ammo.getRed());
        setBlue(getBlue() - ammo.getBlue());
        setYellow(getYellow() - ammo.getYellow());
    }
}
