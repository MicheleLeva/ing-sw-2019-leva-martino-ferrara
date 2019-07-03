package model.exchanges.messages;

/**
 * Message sent to all players to let them choose which map they want to vote for
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class VoteMapMessage extends PlayerMessage {
    public VoteMapMessage(String message){
        super(message);
    }


}
