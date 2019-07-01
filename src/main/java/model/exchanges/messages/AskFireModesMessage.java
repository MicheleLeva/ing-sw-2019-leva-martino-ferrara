package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which fire mode
 * for the current weapon he wants to use
 */
public class AskFireModesMessage extends PlayerMessage {
    public AskFireModesMessage(String message) {
        super(message);
    }
}
