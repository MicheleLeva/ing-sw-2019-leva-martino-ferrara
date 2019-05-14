package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

public class Thor extends WeaponOptional2 {
    String optionalText2;

    public Thor(String name, Ammo baseCost, Ammo optionalCost1,Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                int optionalTargetsNumber2,String baseText,String optionalText1,String optionalText2, Model model) {

        super(name,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,baseText,optionalText1,optionalText2,model);
    }

    @Override
    public void start(Player player) {
        askBaseRequirements(player);
    }

    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        Player player = getModel().getCurrent().getSelectedOptionalTargets1().get(0);
        ArrayList<Player> availableTargets = getModel().getVisiblePlayers(player);
        getModel().optionalThorTargets2(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getOptionalCost1());
        currentPlayer.getActionTree().endAction();
        getModel().notifyShoot(currentPlayer, selectedTargets);
    }

    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        Player player = getModel().getCurrent().getSelectedBaseTargets().get(0);
        ArrayList<Player> availableTargets = getModel().getVisiblePlayers(player);
        getModel().optionalLockRifleTargets1(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getOptionalCost1());
        getModel().optionalThor2(currentPlayer.getPlayerColor(), this);
    }

    @Override
    public String getOptionalText1() {
        return optionalText1;
    }

    @Override
    public String getOptionalText2(){
        return this.optionalText2;
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
        getModel().baseLockRifleTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        getModel().optionalLockRifle1(currentPlayer.getPlayerColor(), this);
    }


}
