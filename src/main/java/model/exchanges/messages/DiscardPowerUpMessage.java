package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which one of
 * the powerUps in his resources to discard
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class DiscardPowerUpMessage extends PlayerMessage {
    public DiscardPowerUpMessage(String message) {
        super(message);
    }
}
