package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Machinegun weapon
 * @author Marco Maria Ferrara
 */
public class Machinegun extends WeaponOptional2 {
    public Machinegun(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2,
                      int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                      int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                      int optionalTargetsNumber2, Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,
                optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }


    /**
     * Asks the requirements of the Base fire mode for the MachineGun
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            //gets all the visible players by the current player
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the MachineGun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the first optional fire mode for the MachineGun
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()==0){
            //gets the players hit during the base fire mode
            ArrayList<Player> availableTargets = getModel().getCurrent().getSelectedBaseTargets();
            getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getOptionalTargetsNumber1());
        }
        else
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the first optional fire Mode for the MachineGun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the second optional fire mode for the MachineGun
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()==0){
            //gets all the visible players by the current player and removes those that he has already shot during
            //the first optional fire mode
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);

            if(!getModel().getCurrent().getSelectedOptionalTargets1().isEmpty()){
                availableTargets.remove(getModel().getCurrent().getSelectedOptionalTargets1().get(0));
                for(Player player : getModel().getCurrent().getSelectedBaseTargets()){
                    if (player != getModel().getCurrent().getSelectedOptionalTargets1().get(0) &&
                            !availableTargets.contains(player))
                        availableTargets.add(player);
                }
            }
            getModel().getCurrent().setAvailableOptionalTargets2(availableTargets);
            getModel().getCurrent().incrementOptionalCounter2();
            getModel().selectTargets(currentPlayer.getPlayerColor(),availableTargets,this.getOptionalTargetsNumber2());
        }
        else
            useOptionalFireMode2(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets2());
    }

    /**
     * Uses the second optional fire Mode for the MachineGun
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

}
