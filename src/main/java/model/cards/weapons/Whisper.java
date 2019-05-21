package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Whisper extends Weapon {
    public Whisper(String name, Ammo pickUpCost,Ammo baseCost, int baseDamage, int baseMarks,
                      int baseTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);

    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistanceMore(2,currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }

        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
