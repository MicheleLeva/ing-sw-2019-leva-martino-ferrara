package model.exchanges.messages;

/**
 * Message that notifies all player's that the current player has completed his shoot action
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ShootMessage extends PlayerMessage {
    public ShootMessage(String message) {
        super(message);
    }
}
