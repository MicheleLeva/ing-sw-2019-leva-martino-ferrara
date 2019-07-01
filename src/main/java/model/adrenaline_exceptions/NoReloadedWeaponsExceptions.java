package model.adrenaline_exceptions;

public class NoReloadedWeaponsExceptions extends Exception{
    /**
     * NoReloadedWeaponsExceptions Class.
     * Thrown when a player tries to shoot, but he or she doesn't have any loaded weapon.
     */
    public NoReloadedWeaponsExceptions(){
        super("You don't have any loaded weapons.\n");
    }
}
