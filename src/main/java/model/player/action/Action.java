package model.player.action;

import model.Model;
import model.player.PlayerColor;

public abstract class Action {
    //action abstract class
    public abstract void perform(Model model , PlayerColor playerColor) throws Exception;
}
