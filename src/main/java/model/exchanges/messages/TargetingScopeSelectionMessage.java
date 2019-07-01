package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which target
 * he wants to use the Targeting Scope on
 */
public class TargetingScopeSelectionMessage extends PlayerMessage{

    public TargetingScopeSelectionMessage(String message) {
        super(message);
    }
}
