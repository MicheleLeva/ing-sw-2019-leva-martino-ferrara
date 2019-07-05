package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

/**
 * Representation of the Cyberblade weapon
 * @author Marco Maria Ferrara
 */
public class Cyberblade extends WeaponOptional2 {

    public Cyberblade(String name,Ammo picUpCost, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage,
                      int optionalDamage1, int optionalDamage2, int baseMarks,
                int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                int optionalTargetsNumber2, Model model) {

        super(name,picUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,
                optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }

    /**
     * Asks the requirements of the Base fire mode for the CyberBlade
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //saves all the players in the same square as the current player in availableTargets
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            //if the player can't hit anyone on his square and has already use his first optional fire mode
            //his shoot action will be counted as executed
            if(availableTargets.isEmpty()&&getModel().getCurrent().getSelectedWeaponSquare()!=null){
                getModel().getGameNotifier().notifyPlayer("No available targets for this base Fire Mode",
                        currentPlayer.getPlayerColor());
                getModel().payFireMode(currentPlayer,this);
                this.getWeaponTree().resetAction();
                getModel().notifyShoot(currentPlayer);
                return;
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the CyberBlade
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the first optional fire mode for the CyberBlade
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            //gets all the squares the player can move to and sends them to him
            ArrayList<Square> squares = Model.runnableSquare(1, currentPlayer.getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());

        }

        else
            useOptionalFireMode1(currentPlayer,null);
    }

    /**
     * Uses the first optional fire Mode for the CyberBlade
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        changePlayerPositionUse(currentPlayer);
    }


    /**
     * Asks the requirements of the second optional fire mode for the CyberBlade
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()== 0) {
            //finds the previously hit player so that he can't be hit again
            Player oldPlayer = getModel().getCurrent().getSelectedBaseTargets().get(0);
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            availableTargets.remove(oldPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useOptionalFireMode2(currentPlayer,getModel().getCurrent().getSelectedOptionalTargets2());
    }

    /**
     * Uses the second optional fire Mode for the CyberBlade
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }



}
