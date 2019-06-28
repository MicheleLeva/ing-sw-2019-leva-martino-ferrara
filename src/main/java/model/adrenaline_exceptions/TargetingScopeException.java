package model.adrenaline_exceptions;

public class TargetingScopeException extends Exception {
    public TargetingScopeException(){
        super("You cant't use the targeting scope now.\n");
    }
}
