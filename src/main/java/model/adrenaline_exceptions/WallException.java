package model.adrenaline_exceptions;

public class WallException extends Exception {
    public WallException(){
        super("You cannot enter a wall.\n");
    }
}
