package model.adrenaline_exceptions;
/**
 * TagbackGrenadeException Class.
 * Thrown when the player tries to use the tagback grenade in his or her turn.
 * @author Michele Leva
 */
public class TagbackGrenadeException extends Exception {

    public TagbackGrenadeException(){
        super("You can't use the tagback grenade now.\n");
    }
}
