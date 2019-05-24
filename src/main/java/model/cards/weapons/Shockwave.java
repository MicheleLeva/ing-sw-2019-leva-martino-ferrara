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
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getAlternativeTargetsNumber());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }

        getModel().payFireMode(currentPlayer,this);

        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
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
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
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
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }

        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
