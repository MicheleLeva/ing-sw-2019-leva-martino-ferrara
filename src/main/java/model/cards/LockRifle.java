package model.cards;

import model.Ammo;
import model.Model;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public class LockRifle extends Weapon {

    String name;
    Ammo baseAmmo;
    Ammo optionalAmmo1;
    int baseDamage;
    int optionalDamage1;
    int baseMarks;
    int optionalMarks1;
    ArrayList<Integer> visibility;
    ArrayList<Integer> visibility1;
    int targetsNumber[];


    public LockRifle(String name,Ammo baseAmmo,Ammo optionalAmmo1,int baseDamage,int optionalDamage1,int baseMarks,
                     int optionalMarks1,ArrayList<Integer> visibility,ArrayList<Integer> visibility1,int[] targetsNumber){
        this.name = name;
        this.baseAmmo = baseAmmo;
        this.optionalAmmo1 = optionalAmmo1;
        this.baseDamage = baseDamage;
        this.optionalDamage1 = optionalDamage1;
        this.baseMarks = baseMarks;
        this.optionalMarks1 = optionalMarks1;
        this.visibility = new ArrayList<>(visibility);
        this.visibility1 = new ArrayList<>(visibility1);
        this.targetsNumber = targetsNumber;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseMarks() {
        return baseMarks;
    }

    public Ammo getBaseCost(){
        return null;
    }

    public void useBaseWeapon(Player currentPlayer, ArrayList<Player> selectedTargets, Weapon weapon){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        //currentPlayer.getResources().removeFromAvailableAmmo(weapon.getBaseCost());
    }

    public void useOptionalWeapon1(Player currentPlayer, ArrayList<Player> selectedTargets, Weapon weapon){
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        //currentPlayer.getResources().removeFromAvailableAmmo(getOptionalCost1());
    }


/*Testing with father class only
    public void getAvailableTargets(Square square){
        ArrayList availableTargets = new ArrayList();
        for(int i = 0; i < visibility.size(); i++){
            if(visibility.get(i) == 0)
                availableTargets.addAll(Model.getModelInstance().getVisiblePlayers(square));
            if(visibility.get(i) == 1)
                availableTargets.addAll(Model.getModelInstance().getPlayersAtDistance(0,square));
            if(visibility.get(i) == 2)
                availableTargets.addAll(Model.getModelInstance().getPlayersInCardinalDirection(square));
            if(visibility.get(i) == 3)
                availableTargets.addAll(Model.getModelInstance().getNonVisiblePlayers(square));


        }
    }
*/
}
