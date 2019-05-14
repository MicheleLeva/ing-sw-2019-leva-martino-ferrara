package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

import static model.Model.getModelInstance;

public class Electroscythe extends WeaponAlternative {

    String alternativeText;
    String baseText;

    public Electroscythe(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber,String baseText,String alternativeText,Model model){
        super(name,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,baseText,alternativeText,model);

    }

    public void askBaseRequirements(Player currentPlayer){
        ArrayList<Player> selectedTargets = getModel().getPlayersInSameSquare(currentPlayer);
        useBaseFireMode(currentPlayer, selectedTargets);
    }

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
            ArrayList<Player> selectedTargets = getModel().getPlayersInSameSquare(currentPlayer);
            useAlternativeFireMode(currentPlayer, selectedTargets);
    }


    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        currentPlayer.getActionTree().endAction();
        getModel().notifyShoot(currentPlayer, getModel().getCurrent().getSelectedBaseTargets());
    }

    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getAlternativeDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getAlternativeMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getAlternativeCost());
        currentPlayer.getActionTree().endAction();
        getModel().notifyShoot(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    public String getBaseText(){ return baseText;}

    public String getAlternativeText(){
        return alternativeText;
    }

}
