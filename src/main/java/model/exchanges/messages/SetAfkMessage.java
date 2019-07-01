package model.exchanges.messages;

/**
 * Message that puts the player in the AFK queue
 */
public class SetAfkMessage extends PlayerMessage{
    public SetAfkMessage(String message){
        super(message);
    }
}
