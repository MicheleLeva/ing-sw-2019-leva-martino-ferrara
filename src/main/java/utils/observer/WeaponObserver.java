package utils.observer;

import model.Current;
import model.cards.weapons.Weapon;
import model.exchanges.events.*;
import model.player.Player;

public interface WeaponObserver {
    void update(WeaponSelectionEvent event);
    void update(TargetsSelectionEvent event);
    void update(ReloadEndTurnEvent reloadEndTurnEvent);
    void update(WeaponReloadEvent weaponReloadEvent);
    void update(OptionalFireModesEvent event);
    void update(ChooseWeaponSquareEvent event);

    void update(WeaponPaymentEvent event);

    void update(ReloadPaymentEvent event);

    void update(PickUpPaymentEvent event);

    void update(ChoosePickUpWeaponEvent event);
}
