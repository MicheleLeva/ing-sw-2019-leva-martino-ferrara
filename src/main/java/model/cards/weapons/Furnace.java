package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Furnace extends WeaponAlternative {
    public Furnace(String name, Ammo pickUpCost,Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
               int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber, Model model){
        super(name,pickUpCost,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,
                alternativeTargetsNumber,model);

    }


    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Square> squares = getModel().runnableSquare(1,currentPlayer.getPosition());
            ArrayList<Square> squaresCopy = new ArrayList<>(squares);
            squares.remove(currentPlayer.getPosition());
            boolean flag;
            for(Square square : squaresCopy) {
                flag = false;
                for(Player player : getModel().getAllPlayers()){
                    if(player.getPosition()==square) {
                        flag = true;
                        break;
                    }

                }
            if(flag == true)continue;
            else squares.remove(square);
            }
            if(squares.isEmpty()){
                getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
                this.getWeaponTree().resetAction();
                getModel().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(this);
                getModel().showFireModes(currentPlayer.getPlayerColor(),this);
                return;
            }

            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(),squares);
        }
        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        ArrayList<Player> allPlayers = getModel().getAllPlayers();

        for(Player target : allPlayers){
            if(target.getPosition()==getModel().getCurrent().getSelectedWeaponSquare()) {
                getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
                getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
            }
        }

        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Square> squares = new ArrayList<>();
            ArrayList<Player> availableTargets = getModel().getPlayersNotInYourRoom(currentPlayer);
            for(Player player : availableTargets){
                if(!squares.contains(player.getPosition()))
                    squares.add(player.getPosition());
            }
            if(squares.isEmpty()){
                getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
                this.getWeaponTree().resetAction();
                getModel().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(this);
                getModel().showFireModes(currentPlayer.getPlayerColor(),this);
                return;
            }
            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        ArrayList<Player> allPlayers = getModel().getAllPlayers();
        for(Player target : allPlayers){
            if(target.getPosition()==getModel().getCurrent().getSelectedWeaponSquare()) {
                getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
                getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
            }
        }

        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }


}
