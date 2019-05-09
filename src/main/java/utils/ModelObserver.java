package utils;

import model.events.message.*;

public interface ModelObserver {
    void update(Message message);
    //riempire con tutti update
}
