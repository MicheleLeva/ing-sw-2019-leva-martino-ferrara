package model.adrenaline_exceptions;

public class TagbackGrenadeException extends Exception {
    /**
     * TagbackGrenadeException Class.
     * Thrown when the player tries to use the tagback grenade in his or her turn.
     */
    public TagbackGrenadeException(){
        super("You can't use the tagback grenade now.\n");
    }
}
