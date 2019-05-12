package model.adrenaline_exceptions;

public class MaxAmmoException extends Exception {
    public MaxAmmoException(String message){
        super(message);
    }
    public MaxAmmoException(){
        super("You have max ammo.\n");
    }
}
