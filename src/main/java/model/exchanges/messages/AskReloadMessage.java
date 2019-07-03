package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which weapon
 * he wants to reload
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class AskReloadMessage extends PlayerMessage {
    public AskReloadMessage(String message){
        super(message);
    }
}
