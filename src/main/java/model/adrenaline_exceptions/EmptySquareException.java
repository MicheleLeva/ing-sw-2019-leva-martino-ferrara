package model.adrenaline_exceptions;

public class EmptySquareException extends Exception {

    public EmptySquareException(){
        super("You cannot grab from an empty square.\n");
    }
}
