package utils.notify;

import model.player.PlayerColor;
import utils.update.PowerUpUpdate;
import java.util.HashMap;
import java.util.Map;

/**
 * Observable class used by the RemotePowerUpHandler
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class PowerUpNotify {
    public final Map<PlayerColor , PowerUpUpdate> listeners = new HashMap<>();

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
