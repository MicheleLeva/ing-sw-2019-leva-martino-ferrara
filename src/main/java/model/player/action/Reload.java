package model.player.action;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;

public class Reload extends Action {
    @Override
    public void perform(Model model, PlayerColor playerColor) throws Exception {
        model.requestWeaponReload(playerColor);
    }
}
