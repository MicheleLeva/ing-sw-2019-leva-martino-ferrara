package model.adrenaline_exceptions;
/**
 * NoReloadedWeaponsExceptions Class.
 * Thrown when a player tries to shoot, but he or she doesn't have any loaded weapon.
 * @author Marco Maria Ferrara
 */
public class NoReloadedWeaponsExceptions extends Exception{

    public NoReloadedWeaponsExceptions(){
        super("You don't have any loaded weapons.\n");
    }
}
