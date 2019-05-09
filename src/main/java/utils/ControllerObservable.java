package utils;

import model.events.playermove.*;

import java.util.ArrayList;

public class ControllerObservable {

    private final ArrayList<ViewObserver> observers = new ArrayList();

    public void register(ViewObserver observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void deregister(ViewObserver observer){
        synchronized(observers){
            observers.remove(observer);
        }
    }

    void notify(PlayerMove move){

    }
    //riempire con tutte notify
}
