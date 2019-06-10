package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Tractorbeam extends WeaponAlternative {
    public Tractorbeam(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }


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

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            target.setPosition(currentPlayer.getPosition());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
        }

        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        System.out.println("tractorbeam 1");
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Square> visibleSquares = getModel().getVisibleSquares(currentPlayer);
            for(Square square : visibleSquares)
                System.out.println("visiible square" + square.getID());
            System.out.println("tractorbeam 1abc");
            ArrayList<Player> allTargets = getModel().getAllPlayers();
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : allTargets){
                ArrayList<Square> playerSquares = getModel().runnableSquare(2,player.getPosition());
                for(Square square : playerSquares){
                    if(visibleSquares.contains(square) && player != currentPlayer) {
                        availableTargets.add(player);
                        break;
                    }
                }
            }
            System.out.println("tractorbeam 1a");

            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            System.out.println("tractorbeam 1b");
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            System.out.println("tractorbeam 1c");
            return;
        }
        if(getModel().getCurrent().getBaseCounter()==1){
            System.out.println("tractorbeam 2");

            ArrayList<Square> selectedSquares = new ArrayList<>();
            ArrayList<Square> visibleSquares = getModel().getVisibleSquares(currentPlayer);
            Player selectedTarget = getModel().getCurrent().getSelectedBaseTargets().get(0);
            ArrayList<Square> playerSquares = getModel().runnableSquare(2,selectedTarget.getPosition());
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

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        System.out.println("tractorbeam 3");

        selectedTargets.get(0).setPosition(getModel().getCurrent().getSelectedWeaponSquare());

        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}

