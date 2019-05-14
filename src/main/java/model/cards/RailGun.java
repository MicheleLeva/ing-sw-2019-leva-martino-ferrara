package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

public class RailGun extends WeaponAlternative {

    String alternativeText;
    String baseText;

    public RailGun(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber,String baseText,String alternativeText, Model model){
        super(name,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,baseText,alternativeText,model);

    }

    @Override
    public String getAlternativeText() {
        return null;
    }

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {

    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
        getModel().baseLockRifleTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

    }
}
