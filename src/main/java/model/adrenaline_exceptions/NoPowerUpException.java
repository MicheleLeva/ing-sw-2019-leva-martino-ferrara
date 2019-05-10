package model.adrenaline_exceptions;

public class NoPowerUpException extends Exception {
    public NoPowerUpException(String message){
        super(message);
    }

    public NoPowerUpException(){
        super("You don't have any PowerUp.\n");
    }
}
