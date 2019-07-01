package model.cards;

import model.game.Ammo;

/**
 * Representation of a generic Ammo Card
 */
public class AmmoCard extends Card{

    private final Ammo ammo;
    private final boolean hasPowerUp;

    /**
     * Constructor for the AmmoCard class
     * @param ammo ammunition contained in the card
     * @param hasPowerUp powerUp contained in the card
     */
    public AmmoCard(Ammo ammo , boolean hasPowerUp){
        this.ammo = ammo;
        this.hasPowerUp = hasPowerUp;
    }

    /**
     * Returns the ammo contained in the card
     * @return the ammo contained in the Ammo Card
     */
    public Ammo getAmmo(){
        return ammo;
    }

    /**
     * Returns whether the card has a powerUp in it or not
     * @return true if the card has a powerUp in it, false otherwise
     */
    public boolean hasPowerUp(){
        return hasPowerUp;
    }
}
