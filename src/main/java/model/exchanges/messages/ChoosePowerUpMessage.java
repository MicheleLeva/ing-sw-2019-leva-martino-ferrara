package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which one of the
 * powerUps in his resources he wants to use
 */
public class ChoosePowerUpMessage extends PlayerMessage {
    public ChoosePowerUpMessage(String message){
        super(message);
    }
}
