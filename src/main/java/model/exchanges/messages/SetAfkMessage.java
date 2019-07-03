package model.exchanges.messages;

/**
 * Message that puts the player in the AFK queue
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class SetAfkMessage extends PlayerMessage{
    public SetAfkMessage(String message){
        super(message);
    }
}
