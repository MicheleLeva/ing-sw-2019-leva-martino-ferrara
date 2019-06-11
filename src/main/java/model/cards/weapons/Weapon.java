package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.cards.Card;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    protected boolean isReloaded;
    protected String name;
    protected Ammo baseCost;
    protected Ammo pickUpCost;
    protected int baseDamage;
    protected int baseMarks;
    protected int baseTargetsNumber;
    private final Model model;
     //todo inserire costo di ricarica  e modificare metodo canReload in Checks
    private WeaponTree weaponTree;


    public void reload(){
        isReloaded = true;
    }

    public void unload(){
        isReloaded = false;
    }

    public Weapon(String name, Ammo pickUpCost, Ammo baseCost,int baseDamage,int baseMarks,int baseTargetsNumber,Model model){
        this.name = name;
        this.baseCost = baseCost;
        this.baseDamage = baseDamage;
        this.baseMarks = baseMarks;
        this.baseTargetsNumber = baseTargetsNumber;
        this.model = model;
        this.pickUpCost = pickUpCost;
        this.isReloaded = true;
        //todo assegnare weapontree
    }

    public String getWeaponName(){
        return name;
    }

    public Ammo getBaseCost(){
        return baseCost;
    }

    public Ammo getPickUpCost(){return pickUpCost;}

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseMarks() {
        return baseMarks;
    }

    public int getBaseTargetsNumber(){
        return baseTargetsNumber;
    }

    public abstract void askBaseRequirements(Player currentPlayer);

    public abstract void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets);

    public Model getModel(){return this.model;}

    public boolean isReloaded(){
        return isReloaded;
    }

    public WeaponTree getWeaponTree(){
        return weaponTree;
    }

    public void setWeaponTree(WeaponTree weaponTree){
        this.weaponTree = weaponTree;
    }

    public int getDamageForUse(String effectType,Weapon weapon){
        switch(effectType){
            case "base":
                return weapon.getBaseDamage();

            case "alternative":
                return ((WeaponAlternative)weapon).getAlternativeDamage();

            case "optional1":
                return ((WeaponOptional1)weapon).getOptionalDamage1();

            case "optional2":
                return ((WeaponOptional2)weapon).getOptionalDamage2();

            default:
                return 0;
        }
    }

    public int getMarksForUse(String effectType,Weapon weapon){
        switch(effectType){
            case "base":
                return weapon.getBaseMarks();

            case "alternative":
                return ((WeaponAlternative)weapon).getAlternativeMarks();

            case "optional1":
                return ((WeaponOptional1)weapon).getOptionalMarks1();

            case "optional2":
                return ((WeaponOptional2)weapon).getOptionalMarks2();

            default:
                return 0;
        }
    }

    public void generalUse(Player currentPlayer, ArrayList<Player> selectedTargets,Weapon weapon,String effectType){
        int damage = getDamageForUse(effectType,weapon);
        int marks = getMarksForUse(effectType,weapon);

        for (Player target : selectedTargets) {
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), damage);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), marks);
        }

        getModel().payFireMode(currentPlayer,weapon);

        getModel().checkNextWeaponAction(weapon, currentPlayer, selectedTargets);
    }

    public void generalUseWithMove(Player currentPlayer, ArrayList<Player> selectedTargets,Weapon weapon,String effectType){
        int damage = getDamageForUse(effectType,weapon);
        int marks = getMarksForUse(effectType,weapon);

        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), damage);
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), marks);
        }

        getModel().payFireMode(currentPlayer,weapon);
        getModel().checkNextWeaponAction(weapon, currentPlayer, selectedTargets);
    }

    public void changePlayerPositionUse(Player currentPlayer, ArrayList<Player> selectedTargets){
        currentPlayer.setPosition(getModel().getCurrent().getSelectedWeaponSquare());

        getModel().payFireMode(currentPlayer,this);

        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    public void endAskSquares(Player currentPlayer, ArrayList<Square> squares, String effectType){
        getModel().getCurrent().setAvailableWeaponSquares(squares);
        switch(effectType){
            case "base":
                getModel().getCurrent().incrementBaseCounter();
                break;

            case "alternative":
                getModel().getCurrent().incrementAlternativeCounter();
                break;

            case "optional1":
                getModel().getCurrent().incrementOptionalCounter1();
                break;

            case "optional2":
                getModel().getCurrent().incrementOptionalCounter2();
                break;
        }
        System.out.println("endasksquares");
        getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
    }

    public void endAskTargets(Player currentPlayer, ArrayList<Player> availableTargets,Weapon weapon,String effectType){
        switch(effectType){
            case "base":
                getModel().getCurrent().setAvailableBaseTargets(availableTargets);
                getModel().getCurrent().incrementBaseCounter();
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, weapon.getBaseTargetsNumber());
                break;

            case "alternative":
                getModel().getCurrent().setAvailableAlternativeTargets(availableTargets);
                getModel().getCurrent().incrementAlternativeCounter();
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, ((WeaponAlternative)weapon).getAlternativeTargetsNumber());
                break;

            case "optional1":
                getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
                getModel().getCurrent().incrementOptionalCounter1();
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, ((WeaponOptional1)weapon).getOptionalTargetsNumber1());
                break;

            case "optional2":
                getModel().getCurrent().setAvailableOptionalTargets2(availableTargets);
                getModel().getCurrent().incrementOptionalCounter2();
                getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, ((WeaponOptional2)weapon).getOptionalTargetsNumber2());
                break;
        }
    }
}




