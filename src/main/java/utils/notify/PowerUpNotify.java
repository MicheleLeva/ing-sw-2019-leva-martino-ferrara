package utils.notify;

import utils.update.PowerUpUpdate;
import utils.update.WeaponUpdate;

import java.util.ArrayList;
import java.util.List;

public class PowerUpNotify {
    public final List<PowerUpUpdate> listeners = new ArrayList();

    public void register(PowerUpUpdate observer){
        synchronized (listeners){
            listeners.add(observer);
        }
    }

    public void deregister(PowerUpUpdate observer){
        synchronized (listeners){
            listeners.remove(observer);
        }
    }
}
