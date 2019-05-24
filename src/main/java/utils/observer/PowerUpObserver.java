package utils.observer;

import model.exchanges.events.*;

public interface PowerUpObserver {
    void update(ChoosePowerUpEvent choosePowerUpEvent);
    void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent);
    void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent);
    void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent);
    void update(TargetingScopeEvent targetingScopeEvent);

    void update(TargetingScopeSelectionEvent event);
}
