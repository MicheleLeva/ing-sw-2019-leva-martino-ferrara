package model.cards;

import model.Ammo;
import model.Model;
import model.events.message.ShowTargetsMessage;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public class LockRifle extends WeaponOptional1 {
    /*
    String name;
    Ammo baseAmmo;
    Ammo optionalAmmo1;
    int baseDamage;
    int optionalDamage1;
    int baseMarks;
    int optionalMarks1;
    ArrayList<Integer> visibility;
    */

    public LockRifle(String name, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                     int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1,String baseText,String optionalText1,Model model) {

        super(name,baseCost,optionalCost1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,baseText,optionalText1,model);
    }

    public void start(Player player){
        askBaseRequirements(player);
    }

    public void askBaseRequirements(Player currentPlayer){
        ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
        getModel().getCurrent().setAvailableBaseTargets(availableTargets);
        getModel().baseLockRifleTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }


    public void askOptionalRequirements1(Player currentPlayer){
        ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
        getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
        getModel().optionalLockRifleTargets1(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //controlla se ha abbastanza munizioni per effettuare opzionale
        if(currentPlayer.getResources().enoughAmmo(this.getOptionalCost1()))
            getModel().optionalLockRifle1(currentPlayer.getPlayerColor(), this);
        else
            getModel().notifyShoot(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        currentPlayer.getResources().removeFromAvailableAmmo(this.getOptionalCost1());
        currentPlayer.getActionTree().endAction();
        ArrayList<Player> shotTargets = new ArrayList<>(getModel().getCurrent().getSelectedOptionalTargets1());
        shotTargets.addAll(getModel().getCurrent().getSelectedBaseTargets());
        getModel().notifyShoot(currentPlayer,shotTargets);
    }

    public String getOptionalText1(){
        return this.optionalText1;
    }


}
