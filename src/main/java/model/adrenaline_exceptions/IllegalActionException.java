package model.adrenaline_exceptions;

public class IllegalActionException extends Exception {
    public IllegalActionException(String message){
        super(message);
    }

    public IllegalActionException(){
        super("This action does not exist.\n");
    }
}
