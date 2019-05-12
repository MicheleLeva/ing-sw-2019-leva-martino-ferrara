package model.adrenaline_exceptions;

public class NoReloadedWeaponsException extends Exception{

        public NoReloadedWeaponsException(String message){
            super(message);
        }
        public NoReloadedWeaponsException(){
            super("You don't have any reloaded weapons.\n");
        }

}
