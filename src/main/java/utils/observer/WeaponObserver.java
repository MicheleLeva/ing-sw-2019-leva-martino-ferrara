package utils.observer;

import model.events.*;

public interface WeaponObserver {
    void update(WeaponSelectionEvent event);
    void update(AlternativeSelectionEvent event);
    void update(BaseLockRifleTargetsEvent event);
    void update(OptionalLockRifleEvent1 event);
    void update(OptionalLockRifleTargetsEvent1 event);
    void update(ReloadEndTurnEvent reloadEndTurnEvent);
    void update(WeaponReloadEvent weaponReloadEvent);
    void update(AlternativeHellionTargetsEvent event);
    void update(OptionalThorEvent2 event);
    void update(OptionalThorTargetsEvent2 event);
    void update(OptionalFireModesEvent event);
    void update(ChooseWeaponSquareEvent event);
}
