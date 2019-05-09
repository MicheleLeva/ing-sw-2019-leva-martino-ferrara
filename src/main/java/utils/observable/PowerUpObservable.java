package utils.observable;

import utils.observer.PowerUpObserver;

import java.util.ArrayList;
import java.util.List;

public class PowerUpObservable {
    public final List<PowerUpObserver> listeners = new ArrayList();

    public void register(PowerUpObserver observer){
        synchronized (listeners){
            listeners.add(observer);
        }
    }

    public void deregister(PowerUpObserver observer){
        synchronized (listeners){
            listeners.remove(observer);
        }
    }
}
