package model.cards;

import model.player_package.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    protected boolean isReloaded;
    protected ArrayList<AmmoColor> reloadCost;

    public void reload(){

    }

    public void setIsReload(){
        isReloaded = true;
    }

    public void shoot(Player opponent){

    }
}
