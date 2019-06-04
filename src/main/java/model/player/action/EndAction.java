package model.player.action;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;

public class EndAction extends Action {
    @Override
    public void perform(Model model , PlayerColor playerColor){
        Player currentPlayer = model.getPlayer(playerColor);
        //ends the action on the action tree
        currentPlayer.getActionTree().endAction();
        //updates the turn
        model.updateTurn();
    }
}
