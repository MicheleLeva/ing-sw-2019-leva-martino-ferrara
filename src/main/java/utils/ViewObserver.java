package utils;
import model.events.playermove.*;

public interface ViewObserver {
    void update(PlayerMove move);
    //riempire con tutti update
}
