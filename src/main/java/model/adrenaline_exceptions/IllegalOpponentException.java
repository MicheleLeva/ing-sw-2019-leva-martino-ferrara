package model.adrenaline_exceptions;

public class IllegalOpponentException extends Exception {
    public IllegalOpponentException(String message){
        super(message);
    }

    public IllegalOpponentException(){
        super("You cannot shoot to this opponent");
    }
}
