package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;

/**
 * Superclass from which all weapons with an alternative fire mode extend
 * @author Marco Maria Ferrara
 */
public abstract class WeaponAlternative extends Weapon {

    protected Ammo alternativeCost;
    protected int alternativeDamage;
    protected int alternativeMarks;
    protected int alternativeTargetsNumber;


    /**
     * Constructor for all weapons with an alternative fire mode
     * @param name name of the weapon
     * @param pickUpCost the cost to pay to pickup the weapon
     * @param baseCost the cost to pay to reload the weapon
     * @param alternativeCost the cost to pay to use the alternative fire mode
     * @param baseDamage amount of damage for the base fire mode
     * @param alternativeDamage amount of damage for the alternative fire mode
     * @param baseMarks number of marks for the base fire mode
     * @param alternativeMarks number of marks for the alternative fire mode
     * @param baseTargetsNumber number of available targets for the base fire mode
     * @param alternativeTargetsNumber number of available targets for the alternative fire mode
     * @param model instance if the Model class
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

