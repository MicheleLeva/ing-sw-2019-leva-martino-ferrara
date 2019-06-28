package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Flamethrower extends WeaponAlternative {
    public Flamethrower(String name, Ammo pickUpCost, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model);

    }


    /**
     * Asks the requirements of the Alternative fire mode for the FlameThrower
     * @param currentPlayer current player
     */
    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Square> availableSquares = new ArrayList<>();
            ArrayList<Square> supportSquares = getModel().getSquaresInCardinal1(currentPlayer);
            if(supportSquares.contains(currentPlayer.getPosition()))
                supportSquares.remove(currentPlayer.getPosition());
            for(Square square : supportSquares){
               for(Player player : getModel().getAllPlayers()){
                   if(player.getPosition() == square)
                       availableSquares.add(square);
               }
            }
            if(availableSquares.isEmpty()){
                getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
                this.getWeaponTree().resetAction();
                getModel().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(this);
                getModel().showFireModes(currentPlayer.getPlayerColor(),this);
                return;
            }
            endAskSquares(currentPlayer,availableSquares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    /**
     * Uses the Alternative fire Mode for the Hellion
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : getModel().getAllPlayers()){
            if(target.getPosition()==getModel().getCurrent().getSelectedWeaponSquare()) {
                getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
                getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
            }
        }
        Square parallelSquare = getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare());
        if(parallelSquare!= null){
            for(Player player : getModel().getAllPlayers()){
                if(player.getPosition()==parallelSquare){
                    getModel().addDamage(currentPlayer.getPlayerColor(), player.getPlayerColor(), 1);
                    getModel().addMark(currentPlayer.getPlayerColor(), player.getPlayerColor(), 0);
                }
            }
        }
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }


    /**
     * Asks the requirements of the Base fire mode for the FlameThrower
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            ArrayList<Player> sameSquarePlayer = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : sameSquarePlayer){
                if(availableTargets.contains(player))
                    availableTargets.remove(player);
            }
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            return;
        }
        if(getModel().getCurrent().getBaseCounter()==1){
            ArrayList<Player> availableTargets = new ArrayList<>();
            Square nextSquare = getModel().getNextPowerGloveSquare(currentPlayer.getPosition(),getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition());
            if(nextSquare == null){
                getModel().getCurrent().incrementBaseCounter();
                askBaseRequirements(currentPlayer);
                return;
            }
            for(Player player : getModel().getAllPlayers()){
                if(player.getPosition() == nextSquare)
                    availableTargets.add(player);
            }
            if(!availableTargets.isEmpty()){
                getModel().getCurrent().setFlamethrowerSupportPlayer(getModel().getCurrent().getSelectedBaseTargets().get(0));
                getModel().getCurrent().setAvailableBaseTargets(availableTargets);
                getModel().getCurrent().incrementBaseCounter();
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            }
            else{
                getModel().getCurrent().incrementBaseCounter();
                askBaseRequirements(currentPlayer);

            }
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Hellion
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
        }
        if(getModel().getCurrent().getFlamethrowerSupportPlayer()!=null)
            getModel().addDamage(currentPlayer.getPlayerColor(),getModel().getCurrent().getFlamethrowerSupportPlayer().getPlayerColor(),this.getBaseDamage());
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
