package model.adrenaline_exceptions;
/**
 * InsufficientAmmoException Class.
 * Thrown when the player hasn't got enough ammo to pay.
 * @author Stefano Martino
 */
public class InsufficientAmmoException extends Exception {


    public InsufficientAmmoException(){
        super("Insufficient Ammo");
    }
}
