package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class Thor extends WeaponOptional2 {

    public Thor(String name,Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1,Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                int optionalTargetsNumber2, Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }

    private Player baseTarget;

    /**
     * Asks the requirements of the second optional fire mode for the Thor
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()==0){
            Player player = getModel().getCurrent().getSelectedOptionalTargets1().get(0);
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(player);
            if(availableTargets.contains(currentPlayer))
                availableTargets.remove(currentPlayer);
            if(availableTargets.contains(baseTarget))
                availableTargets.remove(baseTarget);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the second optional fire Mode for the Thor
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the first optional fire mode for the Thor
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()==0){
            Player player = getModel().getCurrent().getSelectedBaseTargets().get(0);
            baseTarget = player;
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(player);
            if(availableTargets.contains(currentPlayer))
                availableTargets.remove(currentPlayer);
            if(availableTargets.isEmpty()){
                getModel().getGameNotifier().notifyPlayer("No available targets for this Fire Mode ",
                        currentPlayer.getPlayerColor());
                getModel().payFireMode(currentPlayer,this);
                this.getWeaponTree().resetAction();
                getModel().notifyShoot(currentPlayer);
                return;
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the first optional fire Mode for the Thor
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the Base fire mode for the Thor
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the Thor
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
