package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.cards.Card;
import model.player.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    protected boolean isReloaded;
    protected String name;
    protected Ammo baseCost;
    protected Ammo pickUpCost;
    protected int baseDamage;
    protected int baseMarks;
    protected int baseTargetsNumber;
    private final Model model;
     //todo inserire costo di ricarica  e modificare metodo canReload in Checks
    private WeaponTree weaponTree;


    public void reload(){
        isReloaded = true;
    }

    public void unload(){
        isReloaded = false;
    }

    public Weapon(String name, Ammo pickUpCost, Ammo baseCost,int baseDamage,int baseMarks,int baseTargetsNumber,Model model){
        this.name = name;
        this.baseCost = baseCost;
        this.baseDamage = baseDamage;
        this.baseMarks = baseMarks;
        this.baseTargetsNumber = baseTargetsNumber;
        this.model = model;
        this.pickUpCost = pickUpCost;
        //todo assegnare weapontree
    }

    public String getWeaponName(){
        return name;
    }

    public Ammo getBaseCost(){
        return baseCost;
    }

    public Ammo getPickUpCost(){return pickUpCost;}

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseMarks() {
        return baseMarks;
    }

    public int getBaseTargetsNumber(){
        return baseTargetsNumber;
    }

    public abstract void askBaseRequirements(Player currentPlayer);

    public abstract void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets);

    public Model getModel(){return this.model;}

    public boolean isReloaded(){
        return isReloaded;
    }

    public WeaponTree getWeaponTree(){
        return weaponTree;
    }

    public void setWeaponTree(WeaponTree weaponTree){
        this.weaponTree = weaponTree;
    }

}
