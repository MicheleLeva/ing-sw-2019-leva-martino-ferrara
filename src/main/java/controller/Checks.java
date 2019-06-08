package controller;

import model.Ammo;
import model.Model;
import model.cards.*;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;
import model.cards.weapons.WeaponAlternative;
import model.cards.weapons.WeaponOptional1;
import model.cards.weapons.WeaponOptional2;
import model.player.Player;

import java.util.ArrayList;

public class Checks {

    public static boolean enoughAmmo(Card card, Ammo ammo){
        return true;
    }
    private static final int MAX_AMMO = 3;
    private static final int MAX_POWERUP = 3;
    private static final int FIRST_DAMAGE_TRESHOLD = 2;
    private static final int SECOND_DAMADE_TRESHOLD = 6;
    private static final int MAX_DAMAGE = 12;
    private static final int MAX_MARK_FOR_COLOR = 3;
    private static final int KILLSHOT = 11;
    private static final int DOUBLEKILLSHOT = 2;

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

        int drawnRed;
        int drawnBlue;
        int drawnYellow;

        /*if(drawableRed > 0 && grabbedRed > 0){
            if(drawableRed > grabbedRed){
                drawnRed = grabbedRed;
            }
            else{
                drawnRed = drawableRed;
            }
        }*/

        drawnRed = Integer.min(drawableRed,grabbedRed);
        drawnBlue = Integer.min(drawableBlue,grabbedBlue);
        drawnYellow = Integer.min(drawableYellow,grabbedYellow);

        if (drawnRed != 0 || drawnBlue != 0 || drawnYellow != 0){
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



    public static boolean canUseFireMode(Player player, Weapon currentWeapon, String effectType){
        int RED ;
        int YELLOW ;
        int BLUE ;
        Ammo playerAmmo = player.getResources().getAllAmmo();
        RED = playerAmmo.getRed();
        YELLOW = playerAmmo.getYellow();
        BLUE = playerAmmo.getBlue();

        switch (effectType) {
            case "return":
                return true;
            case "end":
                return true;
            case "base":
                return true;
            case "alternative": {
                Ammo cost = ((WeaponAlternative) currentWeapon).getAlternativeCost();
                if (cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW)
                    return true;
                else
                    return false;
            }
            case "optional1": {
                Ammo cost = ((WeaponOptional1) currentWeapon).getOptionalCost1();
                if (cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW)
                    return true;
                else
                    return false;
            }
            case "optional2": {
                Ammo cost = ((WeaponOptional2) currentWeapon).getOptionalCost2();
                if (cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW)
                    return true;
                else
                    return false;
            }

            default:
                return false;

        }
    }

    public static int givenDamage(int playerDamage , int damage){
        if (MAX_DAMAGE - playerDamage < damage){
            return (MAX_DAMAGE - playerDamage);
        }
        else{
            return damage;
        }
    }

    public static int givenMark(int playerMark , int mark){
        if(MAX_MARK_FOR_COLOR - playerMark < mark){
            return (MAX_MARK_FOR_COLOR - playerMark);
        }
        else{
            return mark;
        }
    }

    public static int verifyNewAction(Player player){
        int currentPlayerDamage = player.getPlayerBoard().getDamageCounter().getDamage();
        if(currentPlayerDamage < FIRST_DAMAGE_TRESHOLD){
            return 1;
        }
        if(currentPlayerDamage >= FIRST_DAMAGE_TRESHOLD && currentPlayerDamage < SECOND_DAMADE_TRESHOLD){
            return 2;
        }
        else{
            return 3;
        }
    }

    public static int getMaxDamage(){
        return MAX_DAMAGE;
    }

    public static int getKillshot(){
        return KILLSHOT;
    }

    public static int getDoubleKillShot(){
        return DOUBLEKILLSHOT;
    }

    public static boolean validPayment(Player currentPlayer, ArrayList<Integer> choices,String effectType, Model model) {
        Weapon weapon = model.getCurrent().getSelectedWeapon();
        if (weapon == null){
            weapon = model.getCurrent().getSelectedPickUpWeapon();
        }
        Ammo fireModeCost;
        int powerUpRED = 0;
        int powerUpBLUE = 0;
        int powerUpYELLOW = 0;
        int playerRED = currentPlayer.getResources().getAvailableAmmo().getRed();
        int playerBLUE = currentPlayer.getResources().getAvailableAmmo().getBlue();
        int playerYELLOW = currentPlayer.getResources().getAvailableAmmo().getYellow();
        int fireRED;
        int fireBLUE;
        int fireYELLOW;
        Boolean R;
        Boolean B;
        Boolean Y;
        for (int i : choices) {
            String color = model.getCurrent().getAvailablePaymentPowerUps().get(i-1).getAmmo().toString();
            if (color.equals("RED"))
                powerUpRED++;

            if (color.equals("BLUE"))
                powerUpBLUE++;

            if (color.equals("YELLOW"))
                powerUpYELLOW++;
        }

        switch (effectType) {
            case "reload" :{
                fireModeCost = weapon.getBaseCost();
                break;
            }
            case "pickup" :{
                fireModeCost = weapon.getPickUpCost();
                break;
            }
            case "alternative": {
                fireModeCost = ((WeaponAlternative) weapon).getAlternativeCost();
                break;
            }
            case "optional1": {
                fireModeCost = ((WeaponOptional1) weapon).getOptionalCost1();
                break;
            }
            case "optional2": {
                fireModeCost = ((WeaponOptional2) weapon).getOptionalCost2();
                break;
            }
            default: fireModeCost = new Ammo(0,0,0);
        }

        fireRED = fireModeCost.getRed();
        fireBLUE = fireModeCost.getBlue();
        fireYELLOW = fireModeCost.getYellow();


        if(fireRED-powerUpRED==0)
            R = true;
        else {
            if (fireRED - powerUpRED > 0) {
                if (fireRED - powerUpRED - playerRED <= 0)
                    R = true;
                else
                    R = false;
            } else
                R = false;
        }
        if(fireBLUE-powerUpBLUE==0)
            B = true;
        else{
            if(fireBLUE-powerUpBLUE>0) {
                if (fireBLUE - powerUpBLUE - playerBLUE <= 0)
                    B = true;
                else
                    B = false;
            }
            else
                B = false;
        }
        if(fireYELLOW-powerUpYELLOW==0)
            Y = true;
        else {
            if (fireYELLOW - powerUpYELLOW > 0) {
                if (fireYELLOW - powerUpYELLOW - playerYELLOW <= 0)
                    Y = true;
                else
                    Y = false;
            } else
                Y = false;
        }
        return R&&B&&Y;
    }
}
