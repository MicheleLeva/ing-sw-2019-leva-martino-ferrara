package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Heatseeker weapon
 * @author Marco Maria Ferrara
 */
public class Heatseeker extends Weapon {
    public Heatseeker(String name,Ammo pickUpCost, Ammo baseCost, int baseDamage, int baseMarks,
                    int baseTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Base fire mode for the HeatSeeker
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //finds all the players that are not visible by the current player
            ArrayList<Player> availableTargets = getModel().getNonVisiblePlayers(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the HeatSeeker
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
