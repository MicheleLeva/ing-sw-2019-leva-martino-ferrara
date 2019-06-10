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

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        Player selectedPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
        selectedPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());

        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);    }
}
