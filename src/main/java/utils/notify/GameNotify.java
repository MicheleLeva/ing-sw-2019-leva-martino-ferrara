package utils.notify;

import model.player.PlayerColor;
import utils.update.GameUpdate;

import java.util.HashMap;
import java.util.Map;

public class GameNotify {
    public final Map<PlayerColor, GameUpdate> listeners = new HashMap<>();

    public void register(PlayerColor playerColor , GameUpdate observer){
        synchronized (listeners){
            listeners.put(playerColor , observer);
        }
    }

    public void deregister(PlayerColor playerColor){
        synchronized (listeners){
            listeners.remove(playerColor);
        }
    }
}
