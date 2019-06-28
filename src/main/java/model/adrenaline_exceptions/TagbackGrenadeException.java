package model.adrenaline_exceptions;

public class TagbackGrenadeException extends Exception {
    public TagbackGrenadeException(){
        super("You can't use the tagback grenade now.\n");
    }
}
