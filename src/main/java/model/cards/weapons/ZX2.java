package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class ZX2 extends WeaponAlternative{
    public ZX2(String name,Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,
                alternativeTargetsNumber,model);

    }


    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
