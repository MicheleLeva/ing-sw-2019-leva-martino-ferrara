package controller;

import model.Ammo;
import model.cards.*;
import model.player_package.Player;

import java.util.ArrayList;

public class Checks {

    public static boolean enoughAmmo(Card card, Ammo ammo){
        return true;
    }
    private static final int MAX_AMMO = 3;
    private static final int MAX_POWERUP = 3;
    public static boolean hasMaxWeapon(Player player){
        return true;
    }

    public static boolean hasMaxAmmo(Player player){
        return true;
    }

    public static boolean hasMaxPowerUp(Player player){
        return (player.getResources().getPowerUp().size() == MAX_POWERUP);
    }

    public static Ammo drawnAmmo(Ammo grabbedAmmo , Ammo playerAmmo){
        Ammo drawableAmmo = null;
        int drawableRed;
        int drawableBlue;
        int drawableYellow;

        drawableRed = MAX_AMMO - playerAmmo.getRed();
        drawableBlue = MAX_AMMO - playerAmmo.getBlue();
        drawableYellow = MAX_AMMO - playerAmmo.getYellow();

        int grabbedRed =  grabbedAmmo.getRed();
        int grabbedBlue = grabbedAmmo.getBlue();
        int grabbedYellow = grabbedAmmo.getYellow();

        int drawnRed = 0;
        int drawnBlue = 0;
        int drawnYellow = 0;

        if(drawableRed > 0 && grabbedRed > 0){
            if(drawableRed > grabbedRed){
                drawnRed = grabbedRed;
            }
            else{
                drawnRed = drawableRed;
            }
        }

        if(drawableBlue > 0 && grabbedBlue > 0){
            if(drawableBlue > grabbedBlue){
                drawnBlue = grabbedBlue;
            }
            else{
                drawnBlue = drawableBlue;
            }
        }

        if(drawableYellow > 0 && grabbedYellow > 0){
            if(drawableYellow > grabbedYellow){
                drawnYellow = grabbedYellow;
            }
            else{
                drawnYellow = drawableYellow;
            }
        }

        if (drawnRed != 0 && drawnBlue != 0 && drawnYellow != 0){
            drawableAmmo = new Ammo(drawnRed , drawnBlue , drawnYellow);
        }

        return drawableAmmo;
    }

    public static boolean canReload(ArrayList<Weapon> weapon , Ammo allAmmo){
        for (int i = 0; i < weapon.size(); i++){
            Ammo reloadCost = weapon.get(i).getBaseCost(); //todo ritornare il costo di ricarica
            if (allAmmo.isEnough(reloadCost)){
                return true;
            }
        }
        return false;
    }



    public static boolean canUseFireMode(Weapon currentWeapon, String effectType){
        if(effectType.equals("base"))
            currentWeapon.getBaseCost();
        if(effectType.equals("alternative"))
            ((WeaponAlternative)currentWeapon).getAlternativeCost();
        if(effectType.equals("optional1"))
            ((WeaponOptional1)currentWeapon).getOptionalCost1();
        if(effectType.equals("optional2"))
            ((WeaponOptional2)currentWeapon).getOptionalCost1();
        return true;
    }

    public static int givenDamage(int playerDamage , int damage){
        return 0;
    }

    public static int givenMark(int playerMark , int mark){
        return 0;
    }


}
