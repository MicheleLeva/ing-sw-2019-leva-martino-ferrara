package model.map;

import model.game.CLI;
import model.cards.AmmoCard;
import model.cards.weapons.Weapon;

import java.util.HashMap;



public class Square {
    /**
     * Square Class. It represents the map square.
     */
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
        sides = new HashMap<>();
        squareRow = row;
        squareColumn = column;
        weapons = new Weapon[3];
    }

    /**
     * Sets the square's ID
     * @param ID the square's ID
     */
    public void setID(int ID){
        this.ID = ID;
    }

    /**
     * Returns the weapon on the square
     * @return the weapon on the square
     */
    public Weapon[] getWeapon(){
        return weapons;
    }
    /**
     * Returns the ammo on the square
     * @return the ammo on the square
     */
    public AmmoCard getAmmoCard(){
        return ammo;
    }

    /**
     * Puts the given weapon on the spawn square
     * @param weapon weapon to put on spawn square
     */

    public void setWeapon(Weapon weapon){

         int i = 0;
         while(i < 3 && weapons[i] != null)
             i++;
         if( i == 3)
             return;
         weapons[i] = weapon;

    }

    /**
     * Checks if the square has an no ammo card on it
     * @return true if the current square has no ammo card on it
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
    /**
     * Returns the square's color
     * @return the square's color
     */
    public SquareColor getColor(){

        return this.color;
    }

    /**
     * Sets the square's color
     * @param color the square's color
     */
    public void setColor(SquareColor color){
        this.color = color;
    }

    /**
     * Returns the square adjacent to the given direction
     * @param direction the given direction
     * @return the square adjacent to the given direction
     */
    public Square getSide(Direction direction ){

        return sides.get(direction);
    }

    /**
     * Sets the square adjacent to the given direction
     * @param direction the given direction
     * @param element the adjacent square
     */
    public void setSide(Direction direction, Square element){

        sides.put(direction, element);
    }

    /**
     * Returns the square's row
     * @return the square's row
     */
    public int getSquareRow(){
        return squareRow;
    }
    /**
     * Returns the square's column
     * @return the square's column
     */
    public int getSquareColumn(){
        return squareColumn;
    }

    @Override
    public String toString() {
        return "square";
    }

    /**
     * Checks if the square has neither ammo nor weapons on it
     * @return true if the square is empty, false otherwise
     */
    public boolean isEmpty(){
        if(isSpawn){
            return (weapons[0] == null && weapons[1] == null && weapons[2] == null);
        }
        else
            return (ammo == null);
    }

    /**
     * Returns the square's ID
     * @return the square's ID
     */
    public int getID(){
        return ID;
    }

    /**
     * Sets the ammo card on the square to null
     */
    public void removeAmmoCard(){
        ammo = null;
    }

    /**
     * Shows weapons on the spawn point
     * @return a string that represents the spawn weapons on the screen
     */
    public String showSpawnWeapons(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CLI.getColor(this.color));
        for(int i = 0; i < 3; i++){
            if(getWeapon()[i]!=null){
                stringBuilder.append(getWeapon()[i].getWeaponName());
                stringBuilder.append(" | ");
            }
        }
        stringBuilder.append(CLI.getResetString());
        return stringBuilder.toString();
    }

}
