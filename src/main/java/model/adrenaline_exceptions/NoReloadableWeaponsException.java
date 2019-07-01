package model.adrenaline_exceptions;

public class NoReloadableWeaponsException extends Exception{
    /**
     * NoReloadableWeaponsException Class.
     * Thrown when a player tries to reload a weapon but it he or she doesn't have any reloadable weapon.
     */
        public NoReloadableWeaponsException(){
            super("You don't have any reloadable weapons.\n");

        }

}
