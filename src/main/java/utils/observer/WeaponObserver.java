package utils.observer;

import model.exchanges.events.*;

/**
 * Observer interface implemented by the WeaponController
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public interface WeaponObserver {
    void update(WeaponSelectionEvent event);
    void update(TargetsSelectionEvent event);
    void update(WeaponReloadEvent weaponReloadEvent);
    void update(OptionalFireModesEvent event);
    void update(ChooseWeaponSquareEvent event);
    void update(WeaponPaymentEvent event);
    void update(ReloadPaymentEvent event);
    void update(PickUpPaymentEvent event);
    void update(ChoosePickUpWeaponEvent event);

    void update(WeaponSwapEvent event);
}
