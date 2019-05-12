package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

public abstract class WeaponAlternative extends Weapon {

    protected Ammo alternativeCost;
    protected int alternativeDamage;
    protected int alternativeMarks;
    protected int alternativeTargetsNumber;

    public WeaponAlternative(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                             int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);
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

    public abstract String getAlternativeText();

    @Override
    public void start(Player player) {
        getModel().askAlternativeEffect(player.getPlayerColor(), player.getSelectedWeapon());
    }

    public abstract void askAlternativeRequirements(Player currentPlayer);

    public abstract void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets);

}

