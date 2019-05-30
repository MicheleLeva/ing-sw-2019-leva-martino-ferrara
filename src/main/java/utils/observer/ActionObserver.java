package utils.observer;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;

public interface ActionObserver {
    //void update();
    void update(ActionEvent actionEvent);
    void update(QuitAfkEvent quitAfkEvent);
}
