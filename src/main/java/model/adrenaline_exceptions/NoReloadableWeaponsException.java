package model.adrenaline_exceptions;

public class NoReloadableWeaponsException extends Exception{

        public NoReloadableWeaponsException(){
            super("You don't have any reloadable weapons.\n");

        }

}
