package model.adrenaline_exceptions;
/**
 * EmptySquareException Class.
 * Thrown when the player tries to grab from an empty square.
 * @author Michele Leva
 */
public class EmptySquareException extends Exception {

    public EmptySquareException(){
        super("You cannot grab from an empty square.\n");
    }
}
