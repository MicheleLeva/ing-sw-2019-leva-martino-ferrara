package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;
import java.util.ArrayList;

public class Shockwave extends WeaponAlternative {
    public Shockwave(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }


    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Player> copy = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : copy){
                if(!temp.contains(player))
                    availableTargets.add(player);
            }
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
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Player> copy = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : copy) {
                if (!temp.contains(player))
                    availableTargets.add(player);
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        /*if(getModel().getCurrent().getBaseCounter() == 1) {
            for(Player player : getModel().getCurrent().getSelectedBaseTargets())
                for(Player player2 : getModel().getCurrent().getSelectedBaseTargets())
                    if(player != player2 && player.getPosition()==player2.getPosition()){
                        getModel().getCurrent().decreaseBaseCounter();
                        //todo shockwave targets
                    }

        }
*/
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
