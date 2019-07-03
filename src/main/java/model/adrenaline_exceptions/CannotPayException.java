package model.adrenaline_exceptions;
/**
 * Cannot Pay Exception Class.
 * Thrown when the player cannot pay any of the weapons.
 * @author Marco Maria Ferrara
 */
public class CannotPayException extends Exception {

    public CannotPayException(){
        super("You cannot pay any of the weapons.");
    }
}
