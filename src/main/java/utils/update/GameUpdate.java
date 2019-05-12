package utils.update;

import model.events.GenericMessage;
import model.events.NameSetMessage;
import model.events.RunMessage;
import model.events.ShootMessage;

public interface GameUpdate {
    void update(GenericMessage message);
    void update(RunMessage runMessage);
    void update(NameSetMessage nameSetMessage);
    void update (ShootMessage shootMessage);
}
