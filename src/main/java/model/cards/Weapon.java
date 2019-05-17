package model.cards;

import model.Ammo;
import model.Model;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

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
    protected WeaponTree weaponTree;


    public void reload(){

    }

    public Weapon(String name, Ammo baseCost,int baseDamage,int baseMarks,int baseTargetsNumber,Model model){
        this.name = name;
        this.baseCost = baseCost;
        this.baseDamage = baseDamage;
        this.baseMarks = baseMarks;
        this.baseTargetsNumber = baseTargetsNumber;
        this.model = model;
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

    public void setIsReload(){
        isReloaded = true;
    }

    public boolean isReloaded(){
        return isReloaded;
    }

    public WeaponTree getWeaponTree(){
        return weaponTree;
    }

}
