package model.adrenaline_exceptions;
/**
 * NoPowerUpException Class.
 * Thrown when a player tries to use a power up but it doesn't have any.
 * @author Michele Leva
 */
public class NoPowerUpException extends Exception {

    public NoPowerUpException(){
        super("You don't have any PowerUp.\n");
    }
}
