package utils.observer;

import model.exchanges.events.*;

public interface WeaponObserver {
    void update(WeaponSelectionEvent event);
    //void update(AlternativeSelectionEvent event);
    void update(TargetsSelectionEvent event);
    void update(ReloadEndTurnEvent reloadEndTurnEvent);
    void update(WeaponReloadEvent weaponReloadEvent);
    void update(OptionalFireModesEvent event);
    void update(ChooseWeaponSquareEvent event);
}
