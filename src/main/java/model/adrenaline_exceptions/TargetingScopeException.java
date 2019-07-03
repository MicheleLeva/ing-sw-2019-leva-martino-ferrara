package model.adrenaline_exceptions;
/**
 * TargetingScopeException Class.
 * Thrown when the player cannot use the Targeting Scope.
 * @author Marco Maria Ferrara
 */
public class TargetingScopeException extends Exception {

    public TargetingScopeException(){
        super("You cant't use the targeting scope now.\n");
    }
}
