package utils;


import model.player.PlayerColor;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Observable class used by the Model Notifiers to send messages to different Observer by their PlayerColor
 */
public class ViewObservable<T> {

    private final Map<PlayerColor, Observer> listeners = new HashMap<>();

    public void register(PlayerColor playerColor , Observer observer){
        synchronized (listeners){
            listeners.put(playerColor , observer);
        }
    }

    public void deregister(PlayerColor playerColor){
        synchronized (listeners){
            listeners.remove(playerColor);
        }
    }

    protected void notify(T message){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
                entry.getValue().update(message);
        }
    }

    protected void notify(T message, PlayerColor playerColor){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if (entry.getKey() == playerColor) {
                entry.getValue().update(message);
            }
        }
    }

    protected void notifyOthers(T message, PlayerColor playerColor){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if (entry.getKey() != playerColor) {
                entry.getValue().update(message);
            }
        }
    }

    protected void notifyOthers(T message, PlayerColor p1, PlayerColor p2){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if ((entry.getKey() != p1) && (entry.getKey() != p2)) {
                entry.getValue().update(message);
            }
        }
    }
}
