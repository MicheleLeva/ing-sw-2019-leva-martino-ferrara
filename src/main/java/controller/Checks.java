package controller;

import model.game.Ammo;
import model.game.Model;
import model.cards.weapons.Weapon;
import model.cards.weapons.WeaponAlternative;
import model.cards.weapons.WeaponOptional1;
import model.cards.weapons.WeaponOptional2;
import model.player.Player;

import java.util.ArrayList;

/**
 * Support class that checks the validity of multiple actions
 */
public class Checks {

    private static final int MAX_AMMO = 3;
    private static final int MAX_POWERUP = 3;
    private static final int FIRST_DAMAGE_TRESHOLD = 2;
    private static final int SECOND_DAMADE_TRESHOLD = 6;
    private static final int MAX_DAMAGE = 12;
    private static final int MAX_MARK_FOR_COLOR = 3;
    private static final int KILLSHOT = 11;
    private static final int DOUBLEKILLSHOT = 2;

    /**
     * Checks if the player has the max number of powerUps
     * @param player selected player
     * @return the result is true if the player has 3 powerUps, false otherwise
     */
    public static boolean hasMaxPowerUp(Player player){
        return (player.getResources().getPowerUp().size() == MAX_POWERUP);
    }

    /**
     * Returns the number of ammos actually drawn from the picked up Ammo Card
     * @param grabbedAmmo number of ammos drawn from an Ammo card
     * @param playerAmmo number of ammos the player holds
     */
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

        drawnRed = Integer.min(drawableRed,grabbedRed);
        drawnBlue = Integer.min(drawableBlue,grabbedBlue);
        drawnYellow = Integer.min(drawableYellow,grabbedYellow);

        if (drawnRed != 0 || drawnBlue != 0 || drawnYellow != 0){
            drawableAmmo = new Ammo(drawnRed , drawnBlue , drawnYellow);
        }

        return drawableAmmo;
    }

    /**
     * Checks if the player can reload one of his weapons
     * @param weapon list of the reloadable weapons that the player holds
     * @param allAmmo Sum of players ammos and powerUps
     * @return true if the player can reload, false otherwise
     */
    public static boolean canReload(ArrayList<Weapon> weapon , Ammo allAmmo){
        for (Weapon value : weapon) {
            Ammo reloadCost = value.getBaseCost();
            if (allAmmo.isEnough(reloadCost)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the current player can use the selected Fire Mode
     * @param player current player
     * @param currentWeapon weapon selected by the current player
     * @param effectType string that indicates the type of fire mode selected
     * @return true if the fire mode is usable, false otherwise
     */
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
            case "end":
            case "base":
                return true;
            case "alternative": {
                Ammo cost = ((WeaponAlternative) currentWeapon).getAlternativeCost();
                return cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW;
            }
            case "optional1": {
                Ammo cost = ((WeaponOptional1) currentWeapon).getOptionalCost1();
                return cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW;
            }
            case "optional2": {
                Ammo cost = ((WeaponOptional2) currentWeapon).getOptionalCost2();
                return cost.getRed() <= RED && cost.getBlue() <= BLUE && cost.getYellow() <= YELLOW;
            }

            default:
                return false;

        }
    }

    /**
     * Returns the amount of damage actually given to an opponent
     * @param playerDamage the damage on the opponents' board
     * @param damage the damage given to the opponent
     */
    public static int givenDamage(int playerDamage , int damage){
        if (MAX_DAMAGE - playerDamage < damage){
            return (MAX_DAMAGE - playerDamage);
        }
        else{
            return damage;
        }
    }

    /**
     * Returns the number of marks actually given to an opponent
     * @param playerMark the marks on the opponents' board
     * @param mark the marks given to the opponent
     */
    public static int givenMark(int playerMark , int mark){
        if(MAX_MARK_FOR_COLOR - playerMark < mark){
            return (MAX_MARK_FOR_COLOR - playerMark);
        }
        else{

            return mark;
        }
    }

    /**
     * Finds the available actions based on the amount of damage received by the player
     * @param player current player
     * @return an integer that indicated the set of actions available to the player
     */
    public static int verifyNewAction(Player player){
        int currentPlayerDamage = player.getPlayerBoard().getDamageCounter().getDamage();
        if(currentPlayerDamage < FIRST_DAMAGE_TRESHOLD){
            return 1;
        }
        if(currentPlayerDamage < SECOND_DAMADE_TRESHOLD){
            return 2;
        }
        else{
            return 3;
        }
    }

    /**
     * @return an integer that indicated the max damage receivable
     */
    public static int getMaxDamage(){
        return MAX_DAMAGE;
    }

    /**
     * @return an integer that indicated the amount of damage receivable before getting killed
     */
    public static int getKillshot(){
        return KILLSHOT;
    }

    /**
     * @return the points received from a double kill
     */
    public static int getDoubleKillShot(){
        return DOUBLEKILLSHOT;
    }

    /**
     * Checks if a selected payment is valid or not
     * @param currentPlayer current player in the game
     * @param choices integer that indicates the powerUps selected for a payment
     * @param effectType integer that indicates the type of fire mode selected
     * @return true if the selected payment is valid, false otherwise
     */
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
        boolean R;
        boolean B;
        boolean Y;
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
                System.out.println("alternative in checks valid" );

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

        R = checkValidRed(fireRED,powerUpRED,playerRED);
        B = checkValidBlue(fireBLUE,powerUpBLUE,playerBLUE);
        Y = checkValidYellow(fireYELLOW,powerUpYELLOW,playerYELLOW);

        return R&&B&&Y;
    }

    /**
     * Calculates if the sum of red powerups and red ammos is enough to pay the red fire mode ammo
     */
    private static boolean checkValidRed(int fireRED, int powerUpRED, int playerRED) {

        if (fireRED - powerUpRED == 0)
            return true;
        else {
            if (fireRED - powerUpRED > 0) {
                return fireRED - powerUpRED - playerRED <= 0;
            } else
                return false;
        }
    }

    /**
     * calculates if the sum of blue powerups and blue ammos is enough to pay the blue fire mode ammo
     */
    private static boolean checkValidBlue (int fireBLUE, int powerUpBLUE, int playerBLUE){

        if(fireBLUE-powerUpBLUE==0)
            return true;
        else {
            if (fireBLUE - powerUpBLUE > 0) {
                return fireBLUE - powerUpBLUE - playerBLUE <= 0;
            } else
                return false;
        }
    }

    /**
     * calculates if the sum of yellow powerups and yellow ammos is enough to pay the yellow fire mode ammo
     */
    private static boolean checkValidYellow (int fireYELLOW, int powerUpYELLOW, int playerYELLOW){

        if(fireYELLOW-powerUpYELLOW==0)
            return true;
        else {
            if (fireYELLOW - powerUpYELLOW > 0) {
                return fireYELLOW - powerUpYELLOW - playerYELLOW <= 0;
            } else
                return false;
        }
    }
}
