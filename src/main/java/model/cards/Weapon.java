package model.cards;

import model.Ammo;
import model.player_package.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    private boolean isReloaded;
    protected Ammo reloadCost; //Settati con JSON
    protected Ammo grabCost;

    public boolean isReloaded(){
        return isReloaded;
    }

    public void reload(){

    }

    public void setIsReload(){
        isReloaded = true;
    }

    public void shoot(Player opponent){

    }

    public Ammo getReloadCost(){
        return reloadCost;
    }

    public Ammo getGrabCost(){
        return grabCost;
    }
}
