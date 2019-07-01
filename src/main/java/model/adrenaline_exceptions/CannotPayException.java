package model.adrenaline_exceptions;

public class CannotPayException extends Exception {
    /**
     * Cannot Pay Exception Class.
     * Thrown when the player cannot pay any of the weapons.
     */
    public CannotPayException(){
        super("You cannot pay any of the weapons.");
    }
}
