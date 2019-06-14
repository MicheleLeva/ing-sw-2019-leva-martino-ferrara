package utils;

import java.util.ArrayList;

/**
 * Standard Observable class
 */
public class Observable<T> {

    private final ArrayList<Observer> observers = new ArrayList<>();

    public void register(Observer observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void deregister(Observer observer){
        synchronized(observers){
            observers.remove(observer);
        }
    }

    protected void notify(T message){
        synchronized(observers){
            for (Observer observer : observers){
                observer.update(message);
            }
        }
    }
}
