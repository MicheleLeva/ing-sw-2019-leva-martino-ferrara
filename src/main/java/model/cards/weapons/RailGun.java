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

    private Player firstPlayer ;

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            System.out.println("sono in railgun0");
            ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
            getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            return;
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            System.out.println("sono in railgun1");
            char cardinal = 'x';
            ArrayList<Player> secondAvailableTargets ;
            Player previousPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            this.firstPlayer = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            System.out.println(getModel().getCurrent().getSelectedAlternativeTargets().get(0).getPlayerName());
            if(currentPlayer.getPosition().getSquareRow()==previousPlayer.getPosition().getSquareRow()) {
                if (currentPlayer.getPosition().getSquareColumn() > previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'w';
                if (currentPlayer.getPosition().getSquareColumn() < previousPlayer.getPosition().getSquareColumn())
                    cardinal = 'e';
            }
            else
            if(currentPlayer.getPosition().getSquareColumn()==previousPlayer.getPosition().getSquareColumn()) {
                if (currentPlayer.getPosition().getSquareRow() > previousPlayer.getPosition().getSquareRow())
                    cardinal = 'n';
                if (currentPlayer.getPosition().getSquareColumn() < previousPlayer.getPosition().getSquareColumn())
                    cardinal = 's';
            }
            System.out.println("sono in railgun2");
            secondAvailableTargets = getModel().getPlayersInSelectedCardinal(currentPlayer, cardinal);
            secondAvailableTargets.addAll(getModel().getCurrent().getSelectedAlternativeTargets());
            ArrayList<Player> support = new ArrayList<>(secondAvailableTargets);
            for(Player player : support){
                if(player == currentPlayer || player == previousPlayer)
                    secondAvailableTargets.remove(player);
            }
            System.out.println("sono in railgun4");

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

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInCardinalDirection(currentPlayer);
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
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);

    }
}
