package model.player.action;

import model.game.Model;
import model.player.Player;
import model.player.PlayerColor;

public class EndAction extends Action {
    /**
     * Ends the current action
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     */
    @Override
    public void perform(Model model , PlayerColor playerColor){
        Player currentPlayer = model.getPlayer(playerColor);
        //if the player has passed the turn after having finished all his actions
        if(currentPlayer.getActionTree().isTurnEnded()){
            //the player passes the turn
            currentPlayer.getActionTree().setDoneTurn(true);
            currentPlayer.getActionTree().setMoveEnded(true);

        }
        else
        {
            //ends the action on the action tree
            currentPlayer.getActionTree().endAction();
        }
    }
}
