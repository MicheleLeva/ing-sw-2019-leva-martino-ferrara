package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Railgun weapon
 * @author Marco Maria Ferrara
 */
public class RailGun extends WeaponAlternative {

    public RailGun(String name, Ammo pickUpCost,Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage,
                   int baseMarks,int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,
                baseTargetsNumber,alternativeTargetsNumber,model);

    }

    private Player firstPlayer ;

    /**
     * Asks the requirements of the Base fire mode for the RailGun
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //gets all players in all cardinal directions
            ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the RailGun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        getModel().checkNextWeaponAction(this, currentPlayer);

    }

    /**
     * Asks the requirements of the Alternative fire mode for the RailGun
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            firstPlayer = null;
            //gets all players in all cardinal directions
            ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            return;
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            char cardinal = 'x';
            ArrayList<Player> secondAvailableTargets ;
            Player previousPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            this.firstPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            //determines what cardinal direction was chosen for the previously selected player
            if(currentPlayer.getPosition().getSquareRow()==previousPlayer.getPosition().getSquareRow()) {
                if (currentPlayer.getPosition().getSquareColumn() > previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'w';
                if (currentPlayer.getPosition().getSquareColumn() < previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'e';
            }
            else {
                if (currentPlayer.getPosition().getSquareColumn() == previousPlayer.getPosition().getSquareColumn()) {
                    if (currentPlayer.getPosition().getSquareRow() > previousPlayer.getPosition().getSquareRow()) {
                        cardinal = 'n';
                    }
                    if (currentPlayer.getPosition().getSquareRow() < previousPlayer.getPosition().getSquareRow()) {
                        cardinal = 's';
                    }
                }
            }
            //finds all players in the calculated cardinal direction(removes the previously selected player from
            // this list)
            secondAvailableTargets = getModel().getPlayersInSelectedCardinal(currentPlayer, cardinal);
            secondAvailableTargets.addAll(getModel().getCurrent().getSelectedAlternativeTargets());
            ArrayList<Player> support = new ArrayList<>(secondAvailableTargets);
            for(Player player : support){
                if(player == currentPlayer || player == previousPlayer)
                    secondAvailableTargets.remove(player);
            }

            getModel().getCurrent().setAvailableAlternativeTargets(secondAvailableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            if(secondAvailableTargets.isEmpty()){
                askAlternativeRequirements(currentPlayer);
                return;
            }

            getModel().selectTargets(currentPlayer.getPlayerColor(),secondAvailableTargets, this.getBaseTargetsNumber());

        }
        else {
            if(firstPlayer != null)
                getModel().getCurrent().getSelectedAlternativeTargets().add(firstPlayer);
            useAlternativeFireMode(currentPlayer, getModel().getCurrent().getSelectedAlternativeTargets());
        }
    }

    /**
     * Uses the Alternative fire Mode for the RailGun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }


}
