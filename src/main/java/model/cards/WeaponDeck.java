package model.cards;

import java.util.ArrayList;

public class WeaponDeck extends Deck {

    public ArrayList<Weapon> weaponDeck;

    public WeaponDeck(ArrayList<Weapon> weapons){
        weaponDeck = weapons;
    }

    public Weapon draw(){
        return (weaponDeck.remove(0));
    }
}
