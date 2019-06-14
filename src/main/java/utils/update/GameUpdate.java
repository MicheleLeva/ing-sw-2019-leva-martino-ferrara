package utils.update;

import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.RunMessage;
import model.exchanges.messages.ShootMessage;

/**
 * Observer interface implemented by the GameView
 */
public interface GameUpdate {
    void update(GenericMessage message);
    void update(RunMessage runMessage);
    void update (ShootMessage shootMessage);
}
