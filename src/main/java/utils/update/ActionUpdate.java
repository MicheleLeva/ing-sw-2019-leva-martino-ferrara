package utils.update;

import model.exchanges.messages.ChooseActionMessage;

public interface ActionUpdate {
    void update(ChooseActionMessage chooseActionMessage);
}
