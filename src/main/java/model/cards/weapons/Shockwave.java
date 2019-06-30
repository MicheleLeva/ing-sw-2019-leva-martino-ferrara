package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;
import java.util.ArrayList;

public class Shockwave extends WeaponAlternative {
    public Shockwave(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }

    private ArrayList<Player > finalTargets = new ArrayList<>();

    /**
     * Asks the requirements of the Alternative fire mode for the Shockwave
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : temp){
                availableTargets.remove(player);
            }
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            askAlternativeRequirements(currentPlayer);
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Shockwave
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the Base fire mode for the Shockwave
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            finalTargets.clear();
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : temp){
                availableTargets.remove(player);
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }


        if(getModel().getCurrent().getBaseCounter() == 1) {
            finalTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getCurrent().getAvailableBaseTargets()) {
                boolean flag = false;
                for (Player player2 : finalTargets)
                    if (player2.getPosition() == player.getPosition()) {
                        flag = true;
                    }
                if(!flag)
                    availableTargets.add(player);
            }
            if(availableTargets.isEmpty()){
                getModel().getCurrent().incrementBaseCounter();
                getModel().getCurrent().incrementBaseCounter();
                askBaseRequirements(currentPlayer);
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }


        if(getModel().getCurrent().getBaseCounter() == 2) {
            finalTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getCurrent().getAvailableBaseTargets()) {
                boolean flag = false;
                for (Player player2 : finalTargets)
                    if (player2.getPosition() == player.getPosition()) {
                        flag = true;
                    }
                if(!flag)
                    availableTargets.add(player);
            }
            if(availableTargets.isEmpty()){
                getModel().getCurrent().incrementBaseCounter();
                askBaseRequirements(currentPlayer);
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Shockwave
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, finalTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
