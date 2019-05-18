package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Electroscythe extends WeaponAlternative {

    public Electroscythe(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber,Model model){
        super(name,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

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
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
