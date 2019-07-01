package model.adrenaline_exceptions;

public class InsufficientAmmoException extends Exception {
    /**
     * InsufficientAmmoException Class.
     * Thrown when the player hasn't got enough ammo to pay.
     */

    public InsufficientAmmoException(){
        super("Insufficient Ammo");
    }
}
