package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Electroscythe extends WeaponAlternative {

    public Electroscythe(String name, Ammo pickUpCost,Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber,Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Base fire mode for the Electroscythe
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer){
        ArrayList<Player> selectedTargets = getModel().getPlayersInSameSquare(currentPlayer);
        if(selectedTargets.isEmpty()){
            getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
            this.getWeaponTree().resetAction();
            getModel().resetCurrent();
            getModel().getCurrent().setSelectedWeapon(this);
            getModel().showFireModes(currentPlayer.getPlayerColor(),this);
            return;
        }
        useBaseFireMode(currentPlayer, selectedTargets);
    }

    /**
     * Asks the requirements of the Alternative fire mode for the Electroscythe
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
            ArrayList<Player> selectedTargets = getModel().getPlayersInSameSquare(currentPlayer);
        if(selectedTargets.isEmpty()){
            getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
            this.getWeaponTree().resetAction();
            getModel().resetCurrent();
            getModel().getCurrent().setSelectedWeapon(this);
            getModel().showFireModes(currentPlayer.getPlayerColor(),this);
            return;
        }
            useAlternativeFireMode(currentPlayer, selectedTargets);
    }

    /**
     * Uses the Base fire Mode for the Electroscythe
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Uses the Alternative fire Mode for the Electroscythe
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }
}
