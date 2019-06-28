package model.adrenaline_exceptions;

public class MaxAmmoException extends Exception {
    public MaxAmmoException(){
        super("You have max ammo of the colors given by the drawn card.\n");
    }
}
