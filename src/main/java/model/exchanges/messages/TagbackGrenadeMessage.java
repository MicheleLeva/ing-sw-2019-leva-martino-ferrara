package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which
 * TagBack Grenade in his resources he wants to use
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TagbackGrenadeMessage extends PlayerMessage {

    public TagbackGrenadeMessage(String message){
        super(message);
    }
}
