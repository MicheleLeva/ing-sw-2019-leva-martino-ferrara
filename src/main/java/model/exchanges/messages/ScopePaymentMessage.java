package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose the color of
 * the ammunition cube he wants to pay the Targeting Scope with
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ScopePaymentMessage extends PlayerMessage {
    public ScopePaymentMessage(String message) {
        super(message);
    }
}
