package model.adrenaline_exceptions;

public class NoPowerUpException extends Exception {

    public NoPowerUpException(){
        super("You don't have any PowerUp.\n");
    }
}
