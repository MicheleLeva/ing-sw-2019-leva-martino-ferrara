package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

public abstract class WeaponOptional1 extends Weapon {
    protected int optionalDamage1;
    protected int optionalMarks1;
    protected int optionalTargetsNumber1;
    protected Ammo optionalCost1;
    protected String optionalText1;


    public WeaponOptional1(String name, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                           int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1,Model model){
        super(name,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);
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

    @Override
    public void start(Player player) {
        player.getSelectedWeapon().askBaseRequirements(player);
    }

    public abstract void askOptionalRequirements1(Player currentPlayer);

    public abstract void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets);

    public abstract String getOptionalText1();
}
