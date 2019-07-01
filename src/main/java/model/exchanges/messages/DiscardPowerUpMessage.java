package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which one of
 * the powerUps in his resources to discard
 */
public class DiscardPowerUpMessage extends PlayerMessage {
    public DiscardPowerUpMessage(String message) {
        super(message);
    }
}
