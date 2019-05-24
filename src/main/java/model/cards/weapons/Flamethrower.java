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


    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getAlternativeCounter() == 0) {
            ArrayList<Square> availableSquares = getModel().getSquaresInCardinal1(currentPlayer);

            getModel().getCurrent().setAvailableWeaponSquares(availableSquares);
            getModel().getCurrent().incrementAlternativeCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), availableSquares);
        }

        else
            useAlternativeFireMode(currentPlayer,getModel().getCurrent().getSelectedAlternativeTargets());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : getModel().getAllPlayers()){
            if(target.getPosition()==getModel().getCurrent().getSelectedWeaponSquare()) {
                getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getAlternativeDamage());
                getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getAlternativeMarks());
            }
        }
        Square parallelSquare = getModel().getSquareInSameDirection(currentPlayer.getPosition(),getModel().getCurrent().getSelectedWeaponSquare());
        if(parallelSquare!= null){
            for(Player player : getModel().getAllPlayers()){
                if(player.getPosition()==parallelSquare){
                    getModel().addDamage(currentPlayer.getPlayerColor(), player.getPlayerColor(), 1);
                    getModel().addMark(currentPlayer.getPlayerColor(), player.getPlayerColor(), 0);
                }
            }
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            return;
        }
        if(getModel().getCurrent().getBaseCounter()==1){
            ArrayList<Player> availableTargets = getModel().getPlayersInSameDirection(currentPlayer,getModel().getCurrent().getSelectedBaseTargets().get(0));
            if(!availableTargets.isEmpty()){
                getModel().getCurrent().setAvailableBaseTargets(availableTargets);
                getModel().getCurrent().incrementBaseCounter();
                getModel().getCurrent().setFlamethrowerSupportPlayer(getModel().getCurrent().getSelectedBaseTargets().get(0));
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            }
            else{
                getModel().getCurrent().incrementBaseCounter();
                useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());

            }
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
        if(getModel().getCurrent().getFlamethrowerSupportPlayer()!=null)
        getModel().addDamage(currentPlayer.getPlayerColor(),getModel().getCurrent().getFlamethrowerSupportPlayer().getPlayerColor(),this.getBaseDamage());

        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);//
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
