package utils.observer;

import model.events.ActionEvent;

public interface ActionObserver {
    //void update();
    void update(ActionEvent actionEvent);
}
