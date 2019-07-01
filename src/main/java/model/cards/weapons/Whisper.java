package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Whisper weapon
 */
public class Whisper extends Weapon {
    public Whisper(String name, Ammo pickUpCost,Ammo baseCost, int baseDamage, int baseMarks,
                      int baseTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,baseDamage,baseMarks,baseTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Base fire mode for the Whisper
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Player> visiblePlayers = getModel().getVisiblePlayers(currentPlayer);
            ArrayList<Player> availableTargetsBase = getModel().getPlayersAtDistanceMore(1
                    ,currentPlayer);
            for(Player player : availableTargetsBase)
                if(visiblePlayers.contains(player))
                    availableTargets.add(player);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Whisper
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
