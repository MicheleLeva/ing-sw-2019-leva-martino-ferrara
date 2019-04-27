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

    private Random rand = new Random();


    public Decks(){
        weaponsDeck = new ArrayList<>();
        powerUpDeck = new ArrayList<>();
        ammoCardDeck = new ArrayList<>();
        discardedAmmoCardDeck = new ArrayList<>();
        discardedPowerUpDeck = new ArrayList<>();

    }



    public Weapon drawWeapon(){
        if(weaponsDeck.size()>0)
            return weaponsDeck.remove(rand.nextInt(weaponsDeck.size()));
        else
            return null;

    }

    public PowerUp drawPowerUp(){

        if (powerUpDeck.isEmpty()){
            powerUpDeck.addAll(discardedPowerUpDeck);
            discardedPowerUpDeck.clear();
        }

        return (powerUpDeck.remove(rand.nextInt(powerUpDeck.size())));
    }

    public AmmoCard drawAmmoCard(){

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
