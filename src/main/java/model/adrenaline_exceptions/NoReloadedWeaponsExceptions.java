package model.adrenaline_exceptions;

public class NoReloadedWeaponsExceptions extends Exception{
    public NoReloadedWeaponsExceptions(){
        super("You don't have any loaded weapons.\n");
    }
}
