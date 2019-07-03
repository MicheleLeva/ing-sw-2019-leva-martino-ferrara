package model.exchanges.messages;

/**
 * Message sent by the Action Notifier and received from the Action View to let the player choose which action
 * he wants to use
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChooseActionMessage extends PlayerMessage {
    public ChooseActionMessage(String message){
        super(message);
    }
}
