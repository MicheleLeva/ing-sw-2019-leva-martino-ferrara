package model.adrenaline_exceptions;

public class TargetingScopeException extends Exception {
    /**
     * TargetingScopeException Class.
     * Thrown when the player cannot use the Targeting Scope.
     */
    public TargetingScopeException(){
        super("You cant't use the targeting scope now.\n");
    }
}
