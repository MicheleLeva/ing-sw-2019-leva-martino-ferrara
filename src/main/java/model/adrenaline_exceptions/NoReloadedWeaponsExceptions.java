package model.adrenaline_exceptions;

public class NoReloadedWeaponsExceptions extends Exception{
    public NoReloadedWeaponsExceptions(String message){
        super(message);
    }
    public NoReloadedWeaponsExceptions(){
        super("You don't have any reloaded weapons.\n");

    }
}
