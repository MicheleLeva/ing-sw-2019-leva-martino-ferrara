package utils.notify;

import model.player.PlayerColor;
import utils.update.PowerUpUpdate;
import java.util.HashMap;
import java.util.Map;

public class PowerUpNotify {
    public final Map<PlayerColor , PowerUpUpdate> listeners = new HashMap();

    public void register(PlayerColor playerColor , PowerUpUpdate observer){
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
