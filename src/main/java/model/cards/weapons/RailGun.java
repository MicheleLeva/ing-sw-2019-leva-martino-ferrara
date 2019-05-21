package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class RailGun extends WeaponAlternative {

    public RailGun(String name, Ammo pickUpCost,Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                         int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            char cardinal = 'x';
            ArrayList<Player> secondAvailableTargets ;
            Player previousPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            if(currentPlayer.getPosition().getSquareRow()==previousPlayer.getPosition().getSquareRow()) {
                if (currentPlayer.getPosition().getSquareColumn() > previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'w';
                if (currentPlayer.getPosition().getSquareColumn() < previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'e';
            }
            if(currentPlayer.getPosition().getSquareColumn()==previousPlayer.getPosition().getSquareColumn()) {
                if (currentPlayer.getPosition().getSquareRow() > previousPlayer.getPosition().getSquareRow())
                    cardinal = 'n';
                if (currentPlayer.getPosition().getSquareColumn() < previousPlayer.getPosition().getSquareColumn())
                    cardinal = 's';
            }

            secondAvailableTargets = getModel().getPlayersInSelectedCardinal(currentPlayer, cardinal);
            for(Player player : secondAvailableTargets){
                if(player == currentPlayer)
                    secondAvailableTargets.remove(player);
            }
            secondAvailableTargets.addAll(getModel().getCurrent().getSelectedAlternativeTargets());
            getModel().getCurrent().setAvailableAlternativeTargets(secondAvailableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(),secondAvailableTargets, this.getBaseTargetsNumber());

        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
        getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);

    }
}
