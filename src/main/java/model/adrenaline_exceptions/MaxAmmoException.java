package model.adrenaline_exceptions;
/**
 * MaxAmmoException Class.
 * Thrown when the player has three ammo of at least one ammo color.
 * @author Stefano Martino
 */
public class MaxAmmoException extends Exception {

    public MaxAmmoException(){
        super("You have max ammo of the colors given by the drawn card.\n");
    }
}
