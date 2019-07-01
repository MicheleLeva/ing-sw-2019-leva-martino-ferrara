package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose on which one
 * of the available squares he wants to move the selected Teleporter opponent
 */
public class ChooseTeleporterSquareMessage extends PlayerMessage {
    public ChooseTeleporterSquareMessage(String message){
        super(message);
    }
}
