package model.adrenaline_exceptions;

public class TagbackGrenadeException extends Exception {
    public TagbackGrenadeException(String message){
        super(message);
    }
    public TagbackGrenadeException(){
        super("You can't use the tagback grenade now.\n");
    }
}
