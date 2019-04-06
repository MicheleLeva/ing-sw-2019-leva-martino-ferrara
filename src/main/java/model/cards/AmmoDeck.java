package model.cards;

import java.util.ArrayList;

public class AmmoDeck extends Deck{

    public ArrayList<AmmoCard> ammoDeck;

    public AmmoDeck(ArrayList<AmmoCard> ammoCards){
        ammoDeck = ammoCards;
    }

    public AmmoCard draw(){
        return (ammoDeck.remove(0));
    }
}
