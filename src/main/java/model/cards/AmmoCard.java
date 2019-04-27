package model.cards;

import model.Ammo;

public class AmmoCard extends Card{

    private Ammo ammo;

    private boolean isPowerUp;


    public AmmoCard( int elem1, int elem2,int elem3, boolean elem4){
        ammo = new Ammo(elem1,elem2,elem3);
         isPowerUp = elem4;


    }
    public Ammo getAmmo(){
        return ammo;
    }

    public boolean isPowerUp(){
        return isPowerUp;
    }
}
