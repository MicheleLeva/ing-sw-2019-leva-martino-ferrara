package model.adrenaline_exceptions;

public class EmptySquareException extends Exception {
    public EmptySquareException(String message){
        super(message);
    }

    public EmptySquareException(){
        super("This square is empty");
    }
}
