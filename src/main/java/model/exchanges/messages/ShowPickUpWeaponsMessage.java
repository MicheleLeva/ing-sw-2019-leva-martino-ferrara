package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which weapon
 * he wants to pick up from his position
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ShowPickUpWeaponsMessage extends PlayerMessage {
    public ShowPickUpWeaponsMessage(String message) {
        super(message);
    }
}
