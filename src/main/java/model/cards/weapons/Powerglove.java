package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Powerglove extends WeaponAlternative {
    public Powerglove(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                     int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }

    private Player firstTarget;

    /**
     * Asks the requirements of the Alternative fire mode for the Powerglove
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Square> squares = getModel().getSquaresInCardinal1(currentPlayer);
            squares.remove(currentPlayer.getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getAlternativeCounter() == 1) {
            ArrayList<Player> firstAvailablePlayers = new ArrayList<>();
            for(Player player : getModel().getAllPlayers())
                if(getModel().getCurrent().getSelectedWeaponSquare() == player.getPosition())
                    firstAvailablePlayers.add(player);
            if(firstAvailablePlayers.contains(currentPlayer))
                firstAvailablePlayers.remove(currentPlayer);
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
            if(getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare())!=null)
                lastSquare.add(getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare()));
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
            if(!getModel().getCurrent().getSelectedAlternativeTargets().isEmpty())
                firstTarget = getModel().getCurrent().getSelectedAlternativeTargets().get(0);
            ArrayList<Player> firstAvailablePlayers = new ArrayList<>();
            for(Player player : getModel().getAllPlayers())
                if(getModel().getCurrent().getSelectedWeaponSquare() == player.getPosition())
                    firstAvailablePlayers.add(player);
            if(firstAvailablePlayers.contains(currentPlayer))
                firstAvailablePlayers.remove(currentPlayer);
            if(firstTarget!= null && firstAvailablePlayers.contains(firstTarget))
                firstAvailablePlayers.remove(firstTarget);
            if(firstAvailablePlayers.isEmpty()){
                getModel().getCurrent().incrementAlternativeCounter();
                askAlternativeRequirements(currentPlayer);
                return;
            }

            endAskTargets(currentPlayer,firstAvailablePlayers,this,this.getWeaponTree().getLastAction().getData().getType());
            return;

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
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    /**
     * Asks the requirements of the Base fire mode for the Powerglove
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            ArrayList<Square> squares = getModel().getSquaresInCardinal1(currentPlayer);
            for(Player player : getModel().getAllPlayers()){
                if(squares.contains(player.getPosition()))
                    availableTargets.add(player);
            }
            if(availableTargets.contains(currentPlayer))
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
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
