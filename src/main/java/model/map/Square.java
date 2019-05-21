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

    public Square(int row, int column){
        sides = new HashMap<Direction,Square>();
        squareRow = row;
        squareColumn = column;
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

    //todo
    public void setWeapon(Weapon weapon){

         int i = 0;
         while(weapons[i] != null && i < 3)
             i++;
         if( i == 3)
             return;
         weapons[i] = weapon;

    }



    public void setAmmo(AmmoCard ammoCard){
        ammo = ammoCard;

    }

    public SquareColor getColor(){

        return color;
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

    public void removeAmmoCard(){
        ammo = null;
    }
}
