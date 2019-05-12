package model.player_package;

import model.Model;

public class EndAction extends Action {
    @Override
    public void perform(Model model , PlayerColor playerColor){
        Player currentPlayer = model.getPlayer(playerColor);

        currentPlayer.getActionTree().endAction();
        model.updateTurn();
    }
}
