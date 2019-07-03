package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which opponent
 * he wants to use the Newton PowerUp on
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChooseNewtonOpponentMessage extends PlayerMessage {
    public ChooseNewtonOpponentMessage(String message){
        super(message);
    }
}
