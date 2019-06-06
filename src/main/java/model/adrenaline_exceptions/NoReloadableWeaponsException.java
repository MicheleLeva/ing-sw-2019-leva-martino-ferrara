package model.adrenaline_exceptions;

public class NoReloadableWeaponsException extends Exception{

        public NoReloadableWeaponsException(String message){
            super(message);
        }
        public NoReloadableWeaponsException(){
            super("You don't have any reloadable weapons.\n");

        }

}
