package utils.update;

import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.SetAfkMessage;
import model.exchanges.messages.VoteMapMessage;

/**
 * Observer interface implemented by the ActionView
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public interface ActionUpdate {
    void update(ChooseActionMessage chooseActionMessage);
    void update(SetAfkMessage setAfkMessage);
    void update(VoteMapMessage voteMapMessage);
}
