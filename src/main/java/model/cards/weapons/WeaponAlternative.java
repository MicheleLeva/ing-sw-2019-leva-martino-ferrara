package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

/**
 * Superclass from which all weapons with an alternative fire mode extend
 */
public abstract class WeaponAlternative extends Weapon {

    protected Ammo alternativeCost;
    protected int alternativeDamage;
    protected int alternativeMarks;
    protected int alternativeTargetsNumber;


    /**
     *Constructor for all weapons with an alternative fire mode
     */
    public WeaponAlternative(String name, Ammo pickUpCost,Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                             int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);
        this.alternativeCost = alternativeCost;
        this.alternativeDamage = alternativeDamage;
        this.alternativeMarks = alternativeMarks;
        this.alternativeTargetsNumber = alternativeTargetsNumber;
    }

    public Ammo getAlternativeCost() {
        return alternativeCost;
    }

    public int getAlternativeDamage() {
        return alternativeDamage;
    }

    public int getAlternativeMarks() {
        return alternativeMarks;
    }

    public int getAlternativeTargetsNumber() {
        return alternativeTargetsNumber;
    }

    /**
     * ask the requirements of the alternative fire mode according to the specific weapon
     * @param currentPlayer current player
     */
    public abstract void askAlternativeRequirements(Player currentPlayer);

    /**
     * Uses the alternative fire Mode according to the specific weapon
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the alternative fire Mode
     */
    public abstract void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets);



}

