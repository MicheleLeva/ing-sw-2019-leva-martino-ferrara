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


    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }
}
