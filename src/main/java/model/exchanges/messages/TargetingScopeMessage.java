package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose whether he wants
 * to use the TargetingScope PowerUp
 */
public class TargetingScopeMessage extends PlayerMessage {
    public TargetingScopeMessage(String message) {
        super(message);
    }
}
