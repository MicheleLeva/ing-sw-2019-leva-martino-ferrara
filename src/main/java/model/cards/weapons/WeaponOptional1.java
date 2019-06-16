package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

/**
 * Superclass from which all weapons with 2 optional fire modes extend
 */
public abstract class WeaponOptional1 extends Weapon {
    protected int optionalDamage1;
    protected int optionalMarks1;
    protected int optionalTargetsNumber1;
    protected Ammo optionalCost1;



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
