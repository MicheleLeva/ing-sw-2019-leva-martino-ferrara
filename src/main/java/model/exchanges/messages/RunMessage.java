package model.exchanges.messages;

/**
 * Message sent by the Game Notifier and received from the Game View to let all players know that the current
 * player has used the Run action and show them which of the squares he has moved to
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class RunMessage extends PlayerMessage {
    public RunMessage(String message){
        super(message);
    }
}
