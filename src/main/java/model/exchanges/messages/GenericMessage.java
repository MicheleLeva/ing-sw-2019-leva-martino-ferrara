package model.exchanges.messages;

/**
 * Generic message sent to all players to show information about the current Game
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class GenericMessage extends PlayerMessage {
    public GenericMessage(String message){
        super(message);
    }
}
