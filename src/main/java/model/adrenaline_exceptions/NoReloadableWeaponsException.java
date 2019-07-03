package model.adrenaline_exceptions;
/**
 * NoReloadableWeaponsException Class.
 * Thrown when a player tries to reload a weapon but it he or she doesn't have any reloadable weapon.
 * @author Marco Maria Ferrara
 */
public class NoReloadableWeaponsException extends Exception{

        public NoReloadableWeaponsException(){
            super("You don't have any reloadable weapons.\n");

        }

}
