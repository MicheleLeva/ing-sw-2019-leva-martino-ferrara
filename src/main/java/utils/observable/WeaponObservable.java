package utils.observable;

import utils.observer.WeaponObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by the RemoteWeaponView
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class WeaponObservable {
    public final List<WeaponObserver> listeners = new ArrayList<>();

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
