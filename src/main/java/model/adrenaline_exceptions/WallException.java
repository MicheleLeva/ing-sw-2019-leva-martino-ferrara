package model.adrenaline_exceptions;
/**
 * WallException Class.
 * Thrown when a player tries to walk against a wall.
 * @author Stefano Martino
 */
public class WallException extends Exception {

    public WallException(){
        super("You cannot enter a wall.\n");
    }
}
