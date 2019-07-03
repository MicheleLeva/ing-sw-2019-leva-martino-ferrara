package utils.observer;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;

/**
 * Observer interface implemented by the ActionController
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public interface ActionObserver {
    void update(ActionEvent actionEvent);
    void update(QuitAfkEvent quitAfkEvent);
    void update(VoteMapEvent voteMapEvent);
}
