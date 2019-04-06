package model.map_package;

import model.cards.AmmoCard;
import model.cards.Weapon;
import model.player_package.Figure;

import java.util.HashMap;

public class Square implements MapElement {

    private HashMap<Direction, MapElement> sides;
    public boolean isSpawn;
    private SquareColor color;
    private Weapon[] weapons;
    private AmmoCard ammo;


    public Square enter(Figure figure){
        return this;
    }

    public Weapon[] getWeapon(){
        return weapons;
    }

    public AmmoCard getAmmoCard(){
        return ammo;
    }

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

    public MapElement getSide(Direction direction ){

        return sides.get(direction);
    }

    public void setSide(Direction direction, MapElement element){

        sides.put(direction, element);
    }
}
