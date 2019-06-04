package utils.observer;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;

public interface ActionObserver {
    void update(ActionEvent actionEvent);
    void update(QuitAfkEvent quitAfkEvent);
    void update(VoteMapEvent voteMapEvent);
}
