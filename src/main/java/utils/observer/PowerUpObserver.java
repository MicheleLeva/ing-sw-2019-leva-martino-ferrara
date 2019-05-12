package utils.observer;

import model.events.*;

public interface PowerUpObserver {
    void update(ChoosePowerUpEvent choosePowerUpEvent);
    void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent);
    void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent);
    void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent);
}
