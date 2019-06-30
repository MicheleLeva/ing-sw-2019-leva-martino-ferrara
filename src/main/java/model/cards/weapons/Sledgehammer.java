package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Sledgehammer extends WeaponAlternative {
    public Sledgehammer(String name,Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
               int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,
                alternativeTargetsNumber,model);

    }

    /**
     * Asks the requirements of the Alternative fire mode for the Sledgehammer
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            ArrayList<Square> squares = getModel().getSquaresInCardinal2(currentPlayer);
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Sledgehammer
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        Player selectedPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
        selectedPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeMarks());
        }

        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the Base fire mode for the Sledgehammer
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Sledgehammer
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer);    }
}
