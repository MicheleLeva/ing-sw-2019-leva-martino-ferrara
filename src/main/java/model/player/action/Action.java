package model.player.action;

import model.Model;
import model.player.PlayerColor;

public abstract class Action {

    public abstract void perform(Model model , PlayerColor playerColor) throws Exception;
}
