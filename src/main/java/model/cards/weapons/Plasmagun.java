package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Plasmagun extends WeaponOptional2 {
    public Plasmagun(String name, Ammo picUpCost, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                      int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                      int optionalTargetsNumber2, Model model) {

        super(name,picUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }

    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()== 0) {
            ArrayList<Player> availableTargets = getModel().getCurrent().getSelectedBaseTargets();
            getModel().getCurrent().incrementOptionalCounter2();
            useOptionalFireMode2(currentPlayer,availableTargets);
        }
    }

    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = getModel().runnableSquare(2, currentPlayer.getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }

        else
            useOptionalFireMode1(currentPlayer,null);
    }

    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        changePlayerPositionUse(currentPlayer,selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            if(availableTargets.isEmpty()&&getModel().getCurrent().getSelectedWeaponSquare()!=null){
                getModel().getGameNotifier().notifyGeneric("No available targets for this base Fire Mode");
                getModel().payFireMode(currentPlayer,this);
                getModel().notifyShoot(currentPlayer);
                return;
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

}
