package utils.observer;

import model.exchanges.events.ActionEvent;

public interface ActionObserver {
    //void update();
    void update(ActionEvent actionEvent);
}
