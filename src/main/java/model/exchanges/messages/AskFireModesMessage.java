package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which fire mode
 * for the current weapon he wants to use
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class AskFireModesMessage extends PlayerMessage {
    public AskFireModesMessage(String message) {
        super(message);
    }
}
