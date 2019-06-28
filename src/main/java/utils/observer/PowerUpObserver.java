package utils.observer;

import model.exchanges.events.*;

/**
 * Observer interface implemented by the PowerUpController
 */
public interface PowerUpObserver {
    void update(ChoosePowerUpEvent choosePowerUpEvent);
    void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent);
    void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent);
    void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent);
    void update(TargetingScopeEvent targetingScopeEvent);
    void update(TargetingScopeSelectionEvent event);
    void update(TagbackGrenadeEvent tagbackGrenadeEvent);
    void update(DiscardPowerUpEvent discardPowerUpEvent);

    void update(ScopePaymentEvent event);
}
