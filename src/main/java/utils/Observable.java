package utils;

import model.Model;
import model.events.Message;
import java.util.ArrayList;

public class Observable<T> {

    private final ArrayList<Observer<T>> observers = new ArrayList<Observer<T>>();

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
            for (Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }
}
