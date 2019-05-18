package utils.notify;

import model.player.PlayerColor;
import utils.update.ActionUpdate;

import java.util.HashMap;
import java.util.Map;


public class ActionNotify {
    public final Map<PlayerColor, ActionUpdate> listeners = new HashMap();

    public void register(PlayerColor playerColor , ActionUpdate observer) {
        synchronized (listeners) {
            listeners.put(playerColor , observer);
        }
    }

    public void deregister(PlayerColor playerColor) {
        synchronized (listeners) {
            listeners.remove(playerColor);
        }
    }
}
