package utils.update;

import model.events.RunMessage;

public interface GameUpdate {
    void update(RunMessage runMessage);
}
