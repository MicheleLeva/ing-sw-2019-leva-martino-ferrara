package model.adrenaline_exceptions;

public class WallException extends Exception {
    public WallException(String message){
        super(message);
    }
    public WallException(){
        super("You cannot enter a wall.\n");
    }
}
