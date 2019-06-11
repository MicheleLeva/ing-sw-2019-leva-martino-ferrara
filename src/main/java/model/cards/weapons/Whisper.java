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
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Player> visiblePlayers = getModel().getVisiblePlayers(currentPlayer);
            ArrayList<Player> availableTargetsBase = getModel().getPlayersAtDistanceMore(2,currentPlayer);
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

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
