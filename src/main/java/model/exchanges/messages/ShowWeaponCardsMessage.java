package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which one
 * of the weapons in his resources he wants to shoot with
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ShowWeaponCardsMessage extends PlayerMessage {
    public ShowWeaponCardsMessage(String message){
        super(message);
    }


}
