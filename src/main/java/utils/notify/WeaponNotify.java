package utils.notify;

import utils.update.WeaponUpdate;

import java.util.ArrayList;
import java.util.List;

public class WeaponNotify {
    public final List<WeaponUpdate> listeners = new ArrayList();

    public void register(WeaponUpdate observer){
        synchronized (listeners){
            listeners.add(observer);
        }
    }

    public void deregister(WeaponUpdate observer){
        synchronized (listeners){
            listeners.remove(observer);
        }
    }
}
