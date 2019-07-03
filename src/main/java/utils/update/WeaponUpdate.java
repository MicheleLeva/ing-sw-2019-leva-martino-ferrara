package utils.update;

import model.exchanges.messages.*;

/**
 * Observer interface implemented by the WeaponView
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public interface WeaponUpdate {
    void update(ShowWeaponCardsMessage weaponCardsMessage);
    void update(TargetsSelectionMessage optionalLockRifleTargetsMessage1);
    void update(WeaponReloadMessage weaponReloadMessage);
    void update(AskFireModesMessage message);
    void update(ChooseWeaponSquareMessage message);
    void update(WeaponPaymentMessage message);
    void update(ReloadPaymentMessage message);
    void update(PickUpPaymentMessage message);
    void update(ShowPickUpWeaponsMessage message);

    void update(WeaponSwapMessage message);
}
