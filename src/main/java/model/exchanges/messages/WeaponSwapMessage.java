package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which weapon
 * in his resources he wants to swap with one on the spawn point
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class WeaponSwapMessage extends PlayerMessage {
    public WeaponSwapMessage(String message) {
        super(message);
    }
}
