package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Hellion weapon
 * @author Marco Maria Ferrara
 */
public class Hellion extends WeaponAlternative {

    public Hellion(String name,Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                    int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,
                baseTargetsNumber,alternativeTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Base fire mode for the Hellion
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //gets all visible players that are at least one move away
            ArrayList<Player> finalList = new ArrayList<>();
            ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(0,currentPlayer));
            ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
            for(Player player : temp){
                if(availableTargets.contains(player))
                    finalList.add(player);
            }
            getModel().getCurrent().setAvailableBaseTargets(finalList);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), finalList, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Hellion
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        ArrayList<Player> finalList = new ArrayList<>();
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        for(Player player : getModel().getAllSpawnedPlayers()){
            if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                finalList.add(player);
            }
        }
        for(Player target : finalList){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), 0);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the Alternative fire mode for the Hellion
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            //gets all visible players that are at least one move away
            ArrayList<Player> finalList = new ArrayList<>();
            ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(0,currentPlayer));
            ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
            for(Player player : temp){
                if(availableTargets.contains(player))
                    finalList.add(player);
            }
            getModel().getCurrent().setAvailableAlternativeTargets(finalList);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), finalList, this.getAlternativeTargetsNumber());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Hellion
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        ArrayList<Player> finalList = new ArrayList<>();
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        for(Player player : getModel().getAllSpawnedPlayers()){
            if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                finalList.add(player);
            }
        }
        for(Player target : finalList){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), 0);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer);
    }


}
