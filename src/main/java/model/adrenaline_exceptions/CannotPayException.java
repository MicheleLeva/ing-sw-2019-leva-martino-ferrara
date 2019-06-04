package model.adrenaline_exceptions;

public class CannotPayException extends Exception {
    public CannotPayException(String message){
        super(message);
    }
    public CannotPayException(){
        super("You cannot pay any of the weapons.");
    }
}
