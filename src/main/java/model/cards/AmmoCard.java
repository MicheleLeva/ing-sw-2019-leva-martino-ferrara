package model.cards;

import model.Ammo;

public abstract class AmmoCard extends Card{

    private Ammo ammo;

    public Ammo getAmmo(){
        return ammo;
    }
}
