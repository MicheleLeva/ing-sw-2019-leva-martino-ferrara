package model.map_package;

import model.Cards.AmmoCard;
import model.Cards.Weapon;

import java.util.HashMap;

public class Square extends MapElement {

    private HashMap<Direction, MapElement> sides;
    private boolean isSpawn;
    private SquareColor color;

    public Weapon getWeapon(){
        return null;
    }

    public AmmoCard getAmmoCard(){
        return null;
    }

    public void setWeapon(Weapon w){

    }

    public void setAmmo(AmmoCard ammoCard){

    }

    public SquareColor getColor(){
        return color;
    }
}
