package utils.update;

import model.exchanges.messages.*;

public interface WeaponUpdate {
    void update(ShowWeaponCardsMessage weaponCardsMessage);
    void update(TargetsSelectionMessage optionalLockRifleTargetsMessage1);
    void update(AskReloadMessage askReloadMessage);
    void update(WeaponReloadMessage weaponReloadMessage);
    void update(AskFireModesMessage message);
    void update(ChooseWeaponSquareMessage message);
}
