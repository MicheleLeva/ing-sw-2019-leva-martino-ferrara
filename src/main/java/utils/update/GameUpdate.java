package utils.update;

import model.events.GenericMessage;
import model.events.RunMessage;

public interface GameUpdate {
    void update(GenericMessage message);
    void update(RunMessage runMessage);
}
