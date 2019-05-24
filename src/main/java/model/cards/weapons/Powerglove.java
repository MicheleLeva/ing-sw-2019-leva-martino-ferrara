package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Powerglove extends WeaponAlternative {
    public Powerglove(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
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
            return;
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            ArrayList<Player> availableTargets = getModel().getPlayersInSameDirection(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets().get(0));
            if(!availableTargets.isEmpty()) {
                getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
                getModel().getCurrent().incrementAlternativeCounter();
                getModel().getCurrent().setFlamethrowerSupportPlayer(getModel().getCurrent().getSelectedAlternativeTargets().get(0));
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getAlternativeTargetsNumber());
            }
            else
                useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            currentPlayer.setPosition(target.getPosition());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        if(getModel().getCurrent().getFlamethrowerSupportPlayer()!=null){
            Player target = getModel().getCurrent().getFlamethrowerSupportPlayer();
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Player> copy = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> temp = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : copy){
                if(!temp.contains(player))
                    availableTargets.add(player);
            }
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
            currentPlayer.setPosition(target.getPosition());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
