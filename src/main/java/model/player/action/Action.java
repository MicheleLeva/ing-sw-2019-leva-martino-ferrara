package model.player.action;

import model.game.Model;
import model.player.PlayerColor;

/**
 * Abstract class that represents the player's action
 * @author Stefano Martino
 */
public abstract class Action {
    /**
     * This method performs the action
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws Exception error
     */
    public abstract void perform(Model model , PlayerColor playerColor) throws Exception;
}
