package utils;

import model.events.message.*;

import java.util.ArrayList;

public class ModelObservable {
    private final ArrayList<ModelObserver> observers = new ArrayList();

    public void register(ModelObserver observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void deregister(ModelObserver observer){
        synchronized(observers){
            observers.remove(observer);
        }
    }

    void notify(Message message){

    }
    //riempire con tutte notify
}
