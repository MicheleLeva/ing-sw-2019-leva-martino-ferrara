package model.adrenaline_exceptions;

public class MaxAmmoException extends Exception {
    /**
     * MaxAmmoException Class.
     * Thrown when the player has three ammo of at least one ammo color.
     */
    public MaxAmmoException(){
        super("You have max ammo of the colors given by the drawn card.\n");
    }
}
