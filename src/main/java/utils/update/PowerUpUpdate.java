package utils.update;

import model.exchanges.messages.*;

/**
 * Observer interface implemented by the PowerUpView
 */
public interface PowerUpUpdate {
    void update(ChoosePowerUpMessage choosePowerUpMessage);
    void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage);
    void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage);
    void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage);
    void update(TargetingScopeMessage targetingScopeMessage);
    void update(TargetingScopeSelectionMessage targetingScopeSelectionMessage);
    void update(TagbackGrenadeMessage tagbackGrenadeMessage);
    void update(DiscardPowerUpMessage discardPowerUpMessage);

    void update(ScopePaymentMessage scopePaymentMessage);
}
