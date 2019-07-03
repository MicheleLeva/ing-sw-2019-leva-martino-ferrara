package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Tractorbeam weapon
 * @author Marco Maria Ferrara
 */
public class Tractorbeam extends WeaponAlternative {
    public Tractorbeam(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }


    /**
     * Asks the requirements of the Alternative fire mode for the TractorBeam
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(2,currentPlayer);
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getAlternativeTargetsNumber());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the TractorBeam
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            target.setPosition(currentPlayer.getPosition());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }

        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the Base fire mode for the Electroscythe
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Square> visibleSquares = getModel().getVisibleSquares(currentPlayer);
            ArrayList<Player> allTargets = getModel().getAllSpawnedPlayers();
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : allTargets){
                ArrayList<Square> playerSquares = Model.runnableSquare(2,player.getPosition());
                for(Square square : playerSquares){
                    if(visibleSquares.contains(square) && player != currentPlayer) {
                        availableTargets.add(player);
                        break;
                    }
                }
            }

            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            return;
        }
        if(getModel().getCurrent().getBaseCounter()==1){

            ArrayList<Square> selectedSquares = new ArrayList<>();
            ArrayList<Square> visibleSquares = getModel().getVisibleSquares(currentPlayer);
            Player selectedTarget = getModel().getCurrent().getSelectedBaseTargets().get(0);
            ArrayList<Square> playerSquares = Model.runnableSquare(2,selectedTarget.getPosition());
            for(Square square : playerSquares)
            {
                if(visibleSquares.contains(square)) {
                    selectedSquares.add(square);
                }
            }
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), selectedSquares);
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the TractorBeam
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        selectedTargets.get(0).setPosition(getModel().getCurrent().getSelectedWeaponSquare());

        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer);
    }
}

