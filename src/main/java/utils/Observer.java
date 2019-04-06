package utils;

import model.events.Message;

public interface Observer<T> {

    void update(T message);
}
