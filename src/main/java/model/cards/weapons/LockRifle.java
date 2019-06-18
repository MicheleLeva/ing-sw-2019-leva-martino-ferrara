package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.player.Player;

import java.util.ArrayList;

public class LockRifle extends WeaponOptional1 {

    public LockRifle(String name,Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                     int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1,Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,
                optionalTargetsNumber1,model);
    }

    /**
     * Asks the requirements of the first optional fire mode of Lock Rifle
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        }
        else {
            useBaseFireMode(currentPlayer, getModel().getCurrent().getSelectedBaseTargets());
        }
    }

    /**
     *
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer){
        if(getModel().getCurrent().getOptionalCounter1() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            if(!getModel().getCurrent().getSelectedBaseTargets().isEmpty())
                availableTargets.remove(getModel().getCurrent().getSelectedBaseTargets().get(0));
            getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getOptionalTargetsNumber1());
        }
        else {
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());

        }
    }

    /**
     *
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the base fire Mode for Lock Rifle
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());

        }
        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);

    }

    /**
     *
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the first optional fire Mode for Lock Rifle
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets){
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

}
