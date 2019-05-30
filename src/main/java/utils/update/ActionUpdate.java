package utils.update;

import model.exchanges.messages.ChooseActionMessage;
import model.exchanges.messages.SetAfkMessage;

public interface ActionUpdate {
    void update(ChooseActionMessage chooseActionMessage);
    void update(SetAfkMessage setAfkMessage);
}
