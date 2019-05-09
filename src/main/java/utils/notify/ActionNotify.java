package utils.notify;

import utils.update.ActionUpdate;

import java.util.ArrayList;
import java.util.List;

public class ActionNotify {
    public final List<ActionUpdate> listeners = new ArrayList();

    public void register(ActionUpdate observer) {
        synchronized (listeners) {
            listeners.add(observer);
        }
    }

    public void deregisyer(ActionUpdate observer) {
        synchronized (listeners) {
            listeners.remove(observer);
        }
    }
}
