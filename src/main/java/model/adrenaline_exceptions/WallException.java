package model.adrenaline_exceptions;

public class WallException extends RuntimeException {
    public WallException(String message){
        super(message);
    }
    public WallException(){
        super("You cannot enter a wall");
    }
}
