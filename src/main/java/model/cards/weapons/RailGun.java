package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class RailGun extends WeaponAlternative {

    public RailGun(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

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
        getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

    }
}
