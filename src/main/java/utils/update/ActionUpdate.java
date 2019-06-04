package utils.update;

import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.SetAfkMessage;
import model.exchanges.messages.VoteMapMessage;

public interface ActionUpdate {
    void update(ChooseActionMessage chooseActionMessage);
    void update(SetAfkMessage setAfkMessage);
    void update(VoteMapMessage voteMapMessage);
}
