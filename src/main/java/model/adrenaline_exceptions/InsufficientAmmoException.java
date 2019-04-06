package model.adrenaline_exceptions;

public class InsufficientAmmoException extends Exception {

    public InsufficientAmmoException(String message){
        super(message);
    }

    public InsufficientAmmoException(){
        super("Insufficient Ammo");
    }
}
