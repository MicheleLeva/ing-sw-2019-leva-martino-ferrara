package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

/**
 * Representation of the Shotgun weapon
 * @author Marco Maria Ferrara
 */
public class Shotgun extends  WeaponAlternative{
    public Shotgun(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage,
                   int alternativeDamage, int baseMarks,int alternativeMarks, int baseTargetsNumber,
                   int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,
                baseTargetsNumber,alternativeTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Base fire mode for the Shotgun
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //get all players in the same position of the current player
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getBaseCounter() == 1){
            //gets all the squares at distance one from the previously selected target
            ArrayList<Square> squares = Model.runnableSquare(1,getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Shotgun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());
        }
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the Alternative fire mode for the Shotgun
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            //gets players that are exactly one move away from the current player
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Square> squares = getModel().getSquaresInCardinal1(currentPlayer);
            squares.remove(currentPlayer.getPosition());
            for(Player player : getModel().getAllSpawnedPlayers()){
                if(squares.contains(player.getPosition()))
                    availableTargets.add(player);
            }
            availableTargets.remove(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());

        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Shotgun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }

}
