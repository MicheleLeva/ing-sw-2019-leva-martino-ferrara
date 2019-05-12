package utils.observer;

import model.events.*;

public interface WeaponObserver {
    void update(WeaponSelectionEvent event);
    void update(AlternativeSelectionEvent event);
    void update(BaseLockRifleTargetsEvent event);
    void update(OptionalLockRifleEvent1 event);
    void update(OptionalLockRifleTargetsEvent1 event);
}
