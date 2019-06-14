package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;


/**
 * Superclass from which all weapons with 2 optional fire modes extend
 */
public abstract class WeaponOptional2 extends WeaponOptional1 {
    protected int optionalDamage2;
    protected int optionalMarks2;
    protected int optionalTargetsNumber2;
    protected Ammo optionalCost2;

    public WeaponOptional2(String name, Ammo pickUpCost,Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage,
                           int optionalDamage1, int optionalDamage2, int baseMarks, int optionalMarks1, int optionalMarks2,
                           int baseTargetsNumber, int optionalTargetsNumber1, int optionalTargetsNumber2,Model model) {
        super(name, pickUpCost,baseCost, optionalCost1, baseDamage, optionalDamage1, baseMarks, optionalMarks1,
                baseTargetsNumber, optionalTargetsNumber1,model);

        this.optionalDamage2 = optionalDamage2;
        this.optionalMarks2 = optionalMarks2;
        this.optionalTargetsNumber2 = optionalTargetsNumber2;
        this.optionalCost2 = optionalCost2;

    }

    public Ammo getOptionalCost2() {
        return optionalCost2;
    }

    public int getOptionalDamage2() {
        return optionalDamage2;
    }

    public int getOptionalMarks2() {
        return optionalMarks2;
    }

    public int getOptionalTargetsNumber2() {
        return optionalTargetsNumber2;
    }

    /**
     * Asks the requirements of the second optional fire mode according to the specific weapon
     * @param currentPlayer current player
     */
    public abstract void askOptionalRequirements2(Player currentPlayer);

    /**
     * Uses the second optional fire Mode according to the specific weapon
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    public abstract void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets);

}
