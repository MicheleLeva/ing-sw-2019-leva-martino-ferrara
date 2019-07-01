package model.adrenaline_exceptions;

public class WallException extends Exception {
    /**
     * WallException Class.
     * Thrown when a player tries to walk against a wall.
     */
    public WallException(){
        super("You cannot enter a wall.\n");
    }
}
