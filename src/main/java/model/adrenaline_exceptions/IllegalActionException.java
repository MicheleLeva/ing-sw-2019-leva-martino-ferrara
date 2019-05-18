package model.adrenaline_exceptions;

public class IllegalActionException extends Exception {
    //todo controllare se serve

    public IllegalActionException(String message){
        super(message);
    }

    public IllegalActionException(){
        super("This action does not exist.\n");
    }
}
