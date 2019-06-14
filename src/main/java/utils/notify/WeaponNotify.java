package utils.notify;

import model.player.PlayerColor;
import utils.update.WeaponUpdate;

import java.util.HashMap;
import java.util.Map;

/**
 * Observable class used by the RemoteWeaponHandler
 */
public class WeaponNotify {
    public final Map<PlayerColor,WeaponUpdate> listeners = new HashMap<>();

    public void register(PlayerColor playerColor,WeaponUpdate observer){
        synchronized (listeners){
            listeners.put(playerColor,observer);
        }
    }

    public void deregister(PlayerColor playerColor){
        synchronized (listeners){
            listeners.remove(playerColor);
        }
    }
}
