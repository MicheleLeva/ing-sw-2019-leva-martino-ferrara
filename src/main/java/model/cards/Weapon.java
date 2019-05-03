package model.cards;

import model.Ammo;
import model.Model;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    protected boolean isReloaded;
    protected String name;
    protected Ammo baseAmmo;
    protected Ammo optionalAmmo1;
    protected Ammo optionalAmmo2;
    protected int baseDamage;
    protected int optionalDamage1;
    protected int optionalDamage2;
    protected int baseMarks;
    protected int optionalMarks1;
    protected int optionalMarks2;
    protected ArrayList<Integer> visibility;
    protected ArrayList<Integer> visibility1;
    protected ArrayList<Integer> visibility2;
    protected int[] targetsNumber;

    public void reload(){

    }

    public String getWeaponName(){
        return this.name;
    }

    public Ammo getBaseCost(){
        return baseAmmo;
    }

    public Ammo getOptionalCost1(){
        return optionalAmmo1;
    }

    public Ammo getOptionalCost2(){return optionalAmmo2;}

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getOptionalDamage1() {
        return optionalDamage1;
    }

    public int getOptionalDamage2() {
        return optionalDamage2;
    }

    public int getBaseMarks() {
        return baseMarks;
    }

    public int getOptionalMarks1() {
        return optionalMarks1;
    }

    public int getOptionalMarks2() {
        return optionalMarks2;
    }

    public ArrayList<Integer> getVisibility() {
        return visibility;
    }

    public ArrayList<Integer> getVisibility1() {
        return visibility1;
    }

    public ArrayList<Integer> getVisibility2() {
        return visibility2;
    }

    public int[] getTargetsNumber() {
        return targetsNumber;
    }

    public ArrayList<Player> getAvailableTargets(Square square, int fireMode) {
        ArrayList<Player> availableTargets = new ArrayList<>();
        if(fireMode == 0) {
            for (Integer integer : visibility) {
                if (integer == 0)
                    availableTargets.addAll(Model.getModelInstance().getVisiblePlayers(square));
                if (integer == 1)
                    availableTargets.addAll(Model.getModelInstance().getPlayersAtDistance(0, square));
                if (integer == 2)
                    availableTargets.addAll(Model.getModelInstance().getPlayersInCardinalDirection(square));
                if (integer == 3)
                    availableTargets.addAll(Model.getModelInstance().getNonVisiblePlayers(square));

            }
        }

            if(fireMode == 1) {
                for (Integer integer : visibility1) {
                    if (integer == 0)
                        availableTargets.addAll(Model.getModelInstance().getVisiblePlayers(square));
                    if (integer == 1)
                        availableTargets.addAll(Model.getModelInstance().getPlayersAtDistance(0, square));
                    if (integer == 2)
                        availableTargets.addAll(Model.getModelInstance().getPlayersInCardinalDirection(square));
                    if (integer == 3)
                        availableTargets.addAll(Model.getModelInstance().getNonVisiblePlayers(square));

                }
            }

                if(fireMode == 2) {
                    for (Integer integer : visibility2) {
                        if (integer == 0)
                            availableTargets.addAll(Model.getModelInstance().getVisiblePlayers(square));
                        if (integer == 1)
                            availableTargets.addAll(Model.getModelInstance().getPlayersAtDistance(0, square));
                        if (integer == 2)
                            availableTargets.addAll(Model.getModelInstance().getPlayersInCardinalDirection(square));
                        if (integer == 3)
                            availableTargets.addAll(Model.getModelInstance().getNonVisiblePlayers(square));

                    }
        }

        return availableTargets;
    }

    public void useBaseWeapon(Player currentPlayer, ArrayList<Player> selectedTargets, Weapon weapon){

    }

    public void useOptionalWeapon1(Player currentPlayer, ArrayList<Player> selectedTargets, Weapon weapon){

    }

    public void useOptionalWeapon2(Player currentPlayer, ArrayList<Player> selectedTargets,Weapon weapon){

    }

    public void showWeaponMessage(){

    }

    public void setIsReload(){
        isReloaded = true;
    }

    public boolean isReloaded(){
        return isReloaded;
    }

    public void shoot(Player opponent){

    }
}
