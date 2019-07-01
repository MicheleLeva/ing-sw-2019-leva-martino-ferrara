package model.adrenaline_exceptions;

public class NoPowerUpException extends Exception {
    /**
     * NoPowerUpException Class.
     * Thrown when a player tries to use a power up but it doesn't have any.
     */
    public NoPowerUpException(){
        super("You don't have any PowerUp.\n");
    }
}
