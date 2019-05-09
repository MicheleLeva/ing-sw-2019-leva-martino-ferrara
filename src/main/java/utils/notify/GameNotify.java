package utils.notify;

import utils.update.GameUpdate;

import java.util.ArrayList;
import java.util.List;

public class GameNotify {
    public final List<GameUpdate> listeners = new ArrayList();

    public void register(GameUpdate observer){
        synchronized (listeners){
            listeners.add(observer);
        }
    }

    public void deregister(GameUpdate observer){
        synchronized (listeners){
            listeners.remove(observer);
        }
    }
}
