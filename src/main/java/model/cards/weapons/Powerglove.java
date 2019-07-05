package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Powerglove weapon
 * @author Marco Maria Ferrara
 */
public class Powerglove extends WeaponAlternative {
    public Powerglove(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage,
                      int baseMarks,int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,
                baseTargetsNumber,alternativeTargetsNumber,model);

    }

    private Player firstTarget;



    /**
     * Asks the requirements of the Base fire mode for the Powerglove
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //gets all squares one move away from the current player
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Square> squares = getModel().getSquaresInCardinal1(currentPlayer);
            for(Player player : getModel().getAllSpawnedPlayers()){
                if(squares.contains(player.getPosition()))
                    availableTargets.add(player);
            }
            availableTargets.remove(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }

        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Powerglove
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            currentPlayer.setPosition(target.getPosition());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the Alternative fire mode for the Powerglove
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            firstTarget = null;
            //gets all squares one move away from the current player
            ArrayList<Square> squares = getModel().getSquaresInCardinal1(currentPlayer);
            squares.remove(currentPlayer.getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }


        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            ArrayList<Player> firstAvailablePlayers = new ArrayList<>();
            //gets all players on the previously selected square
            for(Player player : getModel().getAllSpawnedPlayers())
                if(getModel().getCurrent().getSelectedWeaponSquare() == player.getPosition())
                    firstAvailablePlayers.add(player);
            firstAvailablePlayers.remove(currentPlayer);
            //if there is no target on the square the player is asked if he wants to move again
            if(firstAvailablePlayers.isEmpty()){
                getModel().getCurrent().incrementAlternativeCounter();
                askAlternativeRequirements(currentPlayer);
                return;
            }
            endAskTargets(currentPlayer,firstAvailablePlayers,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }


        if(getModel().getCurrent().getAlternativeCounter() == 2) {
            ArrayList<Square> lastSquare = new ArrayList<>();
            //finds the next available square the current player can move to(it has to be in the same direction of
            //the previous square
            if(getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare())!=null)
                lastSquare.add(getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare()));
            //if there is no available square the shoot action ends
            if(lastSquare.isEmpty()) {
                getModel().getCurrent().incrementAlternativeCounter();
                getModel().getCurrent().incrementAlternativeCounter();
                askAlternativeRequirements(currentPlayer);
                return;
            }
            lastSquare.add(getModel().getCurrent().getSelectedWeaponSquare());
            endAskSquares(currentPlayer,lastSquare,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }


        if(getModel().getCurrent().getAlternativeCounter() == 3) {
            //gets all the players in the previously selected square
            if(!getModel().getCurrent().getSelectedAlternativeTargets().isEmpty())
                firstTarget = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            ArrayList<Player> firstAvailablePlayers = new ArrayList<>();
            for(Player player : getModel().getAllSpawnedPlayers())
                if(getModel().getCurrent().getSelectedWeaponSquare() == player.getPosition())
                    firstAvailablePlayers.add(player);
             //if the current player or the previously hit player are in the list of available targets they are removed
            //from that list
            firstAvailablePlayers.remove(currentPlayer);
            if(firstTarget!= null)
                firstAvailablePlayers.remove(firstTarget);
            if(firstAvailablePlayers.isEmpty()){
                getModel().getCurrent().incrementAlternativeCounter();
                askAlternativeRequirements(currentPlayer);
                return;
            }

            endAskTargets(currentPlayer,firstAvailablePlayers,this,this.getWeaponTree().getLastAction().getData().getType());

        }

        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Powerglove
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        if(firstTarget!=null && !selectedTargets.contains(firstTarget)) {
            selectedTargets.add(firstTarget);
        }
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }
        currentPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

}
