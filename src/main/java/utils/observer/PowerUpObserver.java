package utils.observer;

import model.exchanges.events.ChooseNewtonOpponentEvent;
import model.exchanges.events.ChooseNewtonSquareEvent;
import model.exchanges.events.ChoosePowerUpEvent;
import model.exchanges.events.ChooseTeleporterSquareEvent;

public interface PowerUpObserver {
    void update(ChoosePowerUpEvent choosePowerUpEvent);
    void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent);
    void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent);
    void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent);
}
