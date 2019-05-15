package utils.update;

import model.events.*;

public interface WeaponUpdate {
    void update(ShowWeaponCardsMessage weaponCardsMessage);
    void update(AskAlternativeMessage alternativeMessage);
    void update(BaseLockRifleTargetsMessage targetsMessage);
    void update(OptionalLockRifleMessage1 optionalMessage1);
    void update(OptionalLockRifleTargetsMessage1 optionalLockRifleTargetsMessage1);
    void update(AskReloadMessage askReloadMessage);
    void update(WeaponReloadMessage weaponReloadMessage);
    void update(AlternativeHellionTargetsMessage alternativeHellionTargetsMessage);
    void update(OptionalThorMessage2 optionalThorMessage2);
    void update(OptionalThorTargetsMessage2 optionalThorTargetsMessage2);
    void update(AskFireModesMessage message);
    void update(ChooseWeaponSquareMessage message);
}
