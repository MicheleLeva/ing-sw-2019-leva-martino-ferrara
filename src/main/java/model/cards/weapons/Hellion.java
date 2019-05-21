package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Hellion extends WeaponAlternative {

    public Hellion(String name,Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }


    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> finalList = new ArrayList<>();
            ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(1,currentPlayer));
            ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
            for(Player player : availableTargets){
                if(temp.contains(player))
                    finalList.add(player);
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
        ArrayList<Player> finalList = new ArrayList<>();
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        for(Player player : getModel().getAllPlayers()){
            if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                player.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getAlternativeMarks());
                finalList.add(player);
            }
        }
        for(Player target : finalList){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), 0);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getAlternativeCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> finalList = new ArrayList<>();
            ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(1,currentPlayer));
            ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
            for(Player player : availableTargets){
                if(temp.contains(player))
                    finalList.add(player);
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
        ArrayList<Player> finalList = new ArrayList<>();
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        for(Player player : getModel().getAllPlayers()){
            if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                player.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
                finalList.add(player);
            }
        }
        for(Player target : finalList){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), 0);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
