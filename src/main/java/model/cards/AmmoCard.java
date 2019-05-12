package model.cards;

import model.Ammo;

public abstract class AmmoCard extends Card{

    private final Ammo ammo;
    private boolean hasPowerUp;

    public AmmoCard(Ammo ammo , boolean hasPowerUp){
        this.ammo = ammo;
        this.hasPowerUp = hasPowerUp;
    }

    public Ammo getAmmo(){
        return ammo;
    }

    public boolean hasPowerUp(){
        return hasPowerUp;
    }
}
