package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose on which one
 * of the available squares he wants to move the selected Newton opponent
 */
public class ChooseNewtonSquareMessage extends PlayerMessage {
    public ChooseNewtonSquareMessage(String message){
        super(message);
    }
}
