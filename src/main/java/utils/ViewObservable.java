package utils;


import model.player.PlayerColor;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Observable class used by the Model Notifiers to send messages to different Observers by their PlayerColor
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
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

    /**
     * Notify a message to all the listeners.
     * @param message to be notified.
     */
    protected void notify(T message){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
                entry.getValue().update(message);
        }
    }

    /**
     * Notifies a message to the chose listener.
     * @param message to be notified.
     * @param playerColor of the chosen listener.
     */
    protected void notify(T message, PlayerColor playerColor){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if (entry.getKey() == playerColor) {
                entry.getValue().update(message);
            }
        }
    }

    /**
     * Notifies a message to all but the chosen listener.
     * @param message to be notified.
     * @param playerColor of the chosen listener.
     */
    protected void notifyOthers(T message, PlayerColor playerColor){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if (entry.getKey() != playerColor) {
                entry.getValue().update(message);
            }
        }
    }

    /**
     * Notifies a message to all but two chosen listeners.
     * @param message to be notified.
     * @param p1 color of the first listener.
     * @param p2 color of the second listener.
     */
    protected void notifyOthers(T message, PlayerColor p1, PlayerColor p2){
        for(Map.Entry<PlayerColor , Observer> entry : listeners.entrySet()) {
            if ((entry.getKey() != p1) && (entry.getKey() != p2)) {
                entry.getValue().update(message);
            }
        }
    }
}
