package model.adrenaline_exceptions;

public class CannotPayException extends Exception {
    public CannotPayException(){
        super("You cannot pay any of the weapons.");
    }
}
