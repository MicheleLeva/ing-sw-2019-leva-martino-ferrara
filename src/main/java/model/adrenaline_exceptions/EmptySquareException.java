package model.adrenaline_exceptions;

public class EmptySquareException extends Exception {
    /**
     * EmptySquareException Class.
     * Thrown when the player tries to grab from an empty square.
     */
    public EmptySquareException(){
        super("You cannot grab from an empty square.\n");
    }
}
