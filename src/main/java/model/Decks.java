package model;


import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.cards.Weapon;
import java.util.Random;
import java.util.ArrayList;

public class Decks {

    private ArrayList<Weapon> weaponsDeck;
    private ArrayList<PowerUp> powerUpDeck;
    private ArrayList<AmmoCard> ammoCardDeck;
    private ArrayList<PowerUp> discardedPowerUpDeck;
    private ArrayList<AmmoCard> discardedAmmoCardDeck;


    public Decks(){
        weaponsDeck = new ArrayList<>();
        powerUpDeck = new ArrayList<>();
        ammoCardDeck = new ArrayList<>();
        discardedAmmoCardDeck = new ArrayList<>();
        discardedPowerUpDeck = new ArrayList<>();

    }

    public Weapon drawWeapon(){
        Random rand = new Random();
        if(weaponsDeck.size()>0)
            return weaponsDeck.remove(rand.nextInt(weaponsDeck.size()));
        else
            return null;

    }

    public PowerUp drawPowerUp(){
        Random rand = new Random();

        if(powerUpDeck.size() > 0){
        return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
        }
        else
            powerUpDeck = new ArrayList(discardedPowerUpDeck);
            discardedPowerUpDeck.clear();
            return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
    }

    public AmmoCard drawAmmoCard(){
        Random rand = new Random();

        if(ammoCardDeck.size() > 0){
            return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
        }
        else
            ammoCardDeck = new ArrayList(discardedAmmoCardDeck);
        discardedAmmoCardDeck.clear();
        return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
    }

    public ArrayList<Weapon> getWeaponsDeck(){
        return weaponsDeck;

    }

    public ArrayList<PowerUp> getPowerUpDeck(){
        return powerUpDeck;

    }

    public ArrayList<AmmoCard> getAmmoCardDeck(){
        return ammoCardDeck;

    }

    public ArrayList<PowerUp> getDiscardedPowerUpDeck(){
        return discardedPowerUpDeck;

    }

    public ArrayList<AmmoCard> getDiscardedAmmoCardDeck(){
        return discardedAmmoCardDeck;

    }


}
