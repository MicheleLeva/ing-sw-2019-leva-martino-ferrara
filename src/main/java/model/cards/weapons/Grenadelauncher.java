package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Grenadelauncher extends WeaponOptional1 {

    public Grenadelauncher(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                     int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1, Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,
                optionalTargetsNumber1,model);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        if(getModel().getCurrent().getBaseCounter() == 1) {
            ArrayList<Square> squares = getModel().runnableSquare(1, getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition());
            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }


    public void askOptionalRequirements1(Player currentPlayer){
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = getModel().runnableSquare(2, currentPlayer.getPosition());
            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
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
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets){
        currentPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

}
