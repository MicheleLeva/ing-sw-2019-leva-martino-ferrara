package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;

/**
 * Superclass from which all weapons with 2 optional fire modes extend
 * @author Marco Maria Ferrara
 */
public abstract class WeaponOptional1 extends Weapon {
    protected int optionalDamage1;
    protected int optionalMarks1;
    protected int optionalTargetsNumber1;
    protected Ammo optionalCost1;


    /**
     * Constructor for all weapons with only one optional fire mode
     * @param name name of the weapon
     * @param pickUpCost the cost to pay to pickup the weapon
     * @param baseCost the cost to pay to reload the weapon
     * @param optionalCost1 the cost to pay to use the first optional fire mode
     * @param baseDamage amount of damage for the base fire mode
     * @param optionalDamage1 amount of damage for the first optional fire mode
     * @param baseMarks number of marks for the base fire mode
     * @param optionalMarks1 number of marks for the first optional fire mode
     * @param baseTargetsNumber number of available targets for the base fire mode
     * @param optionalTargetsNumber1 number of available targets for the first optional fire mode
     * @param model instance if the Model class
     */
    public WeaponOptional1(String name,Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                           int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1,Model model){
        super(name,pickUpCost,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);
        this.optionalDamage1= optionalDamage1;
        this.optionalCost1=optionalCost1;
        this.optionalMarks1=optionalMarks1;
        this.optionalTargetsNumber1= optionalTargetsNumber1;
    }

    public Ammo getOptionalCost1() {
        return optionalCost1;
    }

    public int getOptionalDamage1() {
        return optionalDamage1;
    }

    public int getOptionalMarks1() {
        return optionalMarks1;
    }

    public int getOptionalTargetsNumber1() {
        return optionalTargetsNumber1;
    }


    /**
     * Asks the requirements of the first optional fire mode according to the specific weapon
     * @param currentPlayer current player
     */
    public abstract void askOptionalRequirements1(Player currentPlayer);

    /**
     * Uses the first optional fire Mode according to the specific weapon
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the first optional fire Mode
     */
    public abstract void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets);


}
