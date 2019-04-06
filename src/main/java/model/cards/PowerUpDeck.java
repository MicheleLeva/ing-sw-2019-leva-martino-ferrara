package model.cards;

import java.util.ArrayList;

public class PowerUpDeck extends Deck{

    public ArrayList<PowerUp> powerUpDeck;

    public PowerUpDeck(ArrayList<PowerUp> powerUps){
        powerUpDeck = powerUps;
    }

    public PowerUp draw(){
        return (powerUpDeck.remove(0));
    }
}
