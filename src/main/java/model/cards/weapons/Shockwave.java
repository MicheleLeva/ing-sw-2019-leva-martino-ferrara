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

    private ArrayList<Player > finalTargets = new ArrayList<>();

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : temp){
                if(availableTargets.contains(player))
                    availableTargets.remove(player);
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
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : temp){
                if(availableTargets.contains(player))
                    availableTargets.remove(player);
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getBaseCounter() == 1) {
            System.out.println("SHOCKWAVEBBB");
            finalTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getCurrent().getAvailableBaseTargets()) {
                boolean flag = false;
                for (Player player2 : finalTargets)
                    if (player2.getPosition() == player.getPosition()) {
                        flag = true;
                    }
                if(flag == false)
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
            System.out.println("SHOCKWAVEAAA");
            finalTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getCurrent().getAvailableBaseTargets()) {
                boolean flag = false;
                for (Player player2 : finalTargets)
                    if (player2.getPosition() == player.getPosition()) {
                        flag = true;
                    }
                if(flag == false)
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

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, finalTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
