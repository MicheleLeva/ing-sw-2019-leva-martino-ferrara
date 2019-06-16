package model.map;

import model.cards.AmmoCard;
import model.cards.weapons.Weapon;

import java.util.HashMap;



public class Square {

    private HashMap<Direction, Square> sides;
    public boolean isSpawn;
    private SquareColor color;
    private Weapon[] weapons;
    private AmmoCard ammo;
    private int squareRow;
    private int squareColumn;
    private int ID;

    /**
     * Constructor for the Square class
     * @param row row of the square in the current map
     * @param column column of the square in the current map
     */
    public Square(int row, int column){
        sides = new HashMap<Direction,Square>();
        squareRow = row;
        squareColumn = column;
        weapons = new Weapon[3];
        //todo controllare il costruttore
        //weapons[0]=null;
        //weapons[1]=null;
        //weapons[2]=null;

    }

    public void setID(int ID){
        this.ID = ID;
    }

    //todo
    public Weapon[] getWeapon(){
        return weapons;
    }

    public AmmoCard getAmmoCard(){
        return ammo;
    }

    /**
     * Puts the given weapon on the spawn square
     * @param weapon weapon to put on spawn square
     */
    //todo
    public void setWeapon(Weapon weapon){

         int i = 0;
         while(i < 3 && weapons[i] != null)
             i++;
         if( i == 3)
             return;
         weapons[i] = weapon;

    }

    /**
     * checks if the square has an less than 3 weapons on it
     */
    public boolean hasEmptyWeaponSlot(){
        for (int i = 0; i<3; i++){
            if (weapons[i] == null){
                    return true;
            }
        }
        return false;
    }

    /**
     * checks if the square has an no ammo card on it
     */
    public boolean hasEmptyAmmoSlot(){
        return (ammo == null);
    }

    /**
     * Puts the given ammo card on the square
     * @param ammoCard ammo card drawn from the ammo card deck
     */
    public void setAmmo(AmmoCard ammoCard){
        ammo = ammoCard;

    }

    public SquareColor getColor(){

        return this.color;
    }

    public void setColor(SquareColor color){
        this.color = color;
    }

    public Square getSide(Direction direction ){

        return sides.get(direction);
    }

    public void setSide(Direction direction, Square element){

        sides.put(direction, element);
    }

    public int getSquareRow(){
        return squareRow;
    }

    public int getSquareColumn(){
        return squareColumn;
    }

    @Override
    public String toString() {
        return "square";
    }

    /**
     * Checks if the square has neither ammo nor weapons on it
     */
    public boolean isEmpty(){
        if(isSpawn){
            return (weapons[0] == null && weapons[1] == null && weapons[2] == null);
        }
        else
            return (ammo == null);
    }

    public int getID(){
        return ID;
    }

    /**
     * Sets the ammo card on the square to null
     */
    public void removeAmmoCard(){
        ammo = null;
    }

}
