package utils.observable;

import model.events.Event;
import utils.observer.ActionObserver;

import java.util.ArrayList;
import java.util.List;

public class ActionObservable {
    public final List<ActionObserver> listeners = new ArrayList();

    public void register(ActionObserver observer) {
        synchronized (listeners) {
            listeners.add(observer);
        }
    }

    public void deregister(ActionObserver observer) {
        synchronized (listeners) {
            listeners.remove(observer);
        }
    }



}
