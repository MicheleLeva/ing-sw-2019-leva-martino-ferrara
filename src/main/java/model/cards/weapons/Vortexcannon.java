package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Vortexcannon extends WeaponOptional1{
    public Vortexcannon(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                     int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1, Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,
                optionalTargetsNumber1,model);
    }

    public void start(Player player){
        askBaseRequirements(player);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Square> availableSquares = getModel().getVisibleSquares(currentPlayer);
            ArrayList<Square> chosenSquares = new ArrayList<>();
            if(availableSquares.contains(currentPlayer.getPosition()))
                availableSquares.remove(currentPlayer.getPosition());
            for(Square square : availableSquares){
                for(Player player : getModel().getAllPlayers())
                    if(getModel().runnableSquare(1, square).contains(player.getPosition()))
                        chosenSquares.add(square);

            }
            getModel().getCurrent().setAvailableWeaponSquares(chosenSquares);
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(),chosenSquares);

        }
        if(getModel().getCurrent().getBaseCounter() == 1){
            Square square = getModel().getCurrent().getSelectedWeaponSquare();
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer,square);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }


    public void askOptionalRequirements1(Player currentPlayer){
        if(getModel().getCurrent().getOptionalCounter1() == 0) {
            Square square = getModel().getCurrent().getSelectedWeaponSquare();
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1,currentPlayer,square);
            getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getOptionalTargetsNumber1());
        }
        else
            useOptionalFireMode1(currentPlayer,getModel().getCurrent().getSelectedOptionalTargets1());
    }

    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){

        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);        //
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets){
        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }
}
