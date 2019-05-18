package utils.observable;

import utils.observer.WeaponObserver;

import java.util.ArrayList;
import java.util.List;

public class WeaponObservable {
    public final List<WeaponObserver> listeners = new ArrayList();

    public void register(WeaponObserver observer){
        synchronized (listeners){
            listeners.add(observer);
        }
    }

    public void deregister(WeaponObserver observer){
        synchronized (listeners){
            listeners.remove(observer);
        }
    }

}
