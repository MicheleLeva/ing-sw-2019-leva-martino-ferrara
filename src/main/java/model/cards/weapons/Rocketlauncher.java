package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Rocketlauncher extends WeaponOptional2 {
    public Rocketlauncher(String name, Ammo picUpCost, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                     int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                     int optionalTargetsNumber2, Model model) {

        super(name,picUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }

    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()== 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getAllPlayers())
                if(player.getPosition() == getModel().getCurrent().getRocketlauncherSquare())
                    availableTargets.add(player);
            availableTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            getModel().getCurrent().setAvailableOptionalTargets2(availableTargets);
            getModel().getCurrent().incrementOptionalCounter2();
            useOptionalFireMode2(currentPlayer,availableTargets);
        }
    }

    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(),target.getPlayerColor(),this.getOptionalDamage2());
            getModel().addMark(currentPlayer.getPlayerColor(),target.getPlayerColor(),getOptionalMarks2());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = getModel().runnableSquare(2, currentPlayer.getPosition());
            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
        }

        else
            useOptionalFireMode1(currentPlayer,null);
    }

    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        currentPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
        //Pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            if(availableTargets.contains(currentPlayer))
                availableTargets.remove(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        if(getModel().getCurrent().getBaseCounter()== 1) {
            ArrayList<Square> squares = getModel().runnableSquare(1, currentPlayer.getPosition());
            getModel().getCurrent().setAvailableWeaponSquares(squares);
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        for(Player target : selectedTargets){
            getModel().getCurrent().setRocketlauncherSquare(currentPlayer.getPosition());
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(),target.getPlayerColor(),this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(),target.getPlayerColor(),getBaseMarks());
        }
        //sistemare il pagamento
        getModel().payFireMode(currentPlayer,this);
        //
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
    }

}
