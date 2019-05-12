package utils.notify;

import model.player_package.PlayerColor;
import utils.update.WeaponUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
