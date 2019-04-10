package model;

import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.cards.Weapon;

import java.util.ArrayList;

public class Decks {

    private ArrayList<Weapon> weaponsDeck;
    private ArrayList<PowerUp> powerUpDeck;
    private ArrayList<AmmoCard> ammoCardDeck;
    private ArrayList<PowerUp> discardedPowerUpDeck;
    private ArrayList<AmmoCard> discardedAmmoCardDeck;

    public Weapon drawWeapon(){
        return weaponsDeck.remove(0);
    }

    public PowerUp drawPowerUp(){
        return powerUpDeck.remove(0);
    }

    public AmmoCard drawAmmoCard(){
        return ammoCardDeck.remove(0);
    }

    public Decks(){

    }


}
