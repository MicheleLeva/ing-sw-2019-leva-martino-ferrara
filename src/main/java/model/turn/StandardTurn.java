package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;

public class StandardTurn extends Turn {

    public StandardTurn(Model model){
        super(model);
    }

    @Override
    public synchronized void startTurn() {
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        //Mostra al giocatore la lista comandi
        getModel().printMessage(currentPlayerColor, KeyMap.getCommandList(), "");

        /*while (!currentPlayer.getActionTree().isEnd()) {
            //Mostra al giocatore le azioni disponibili
            getModel().printMessage(currentPlayerColor, currentPlayer.getActionTree().availableAction(), "");
            //Chiedi input
            getModel().askTurnInput(currentPlayerColor);
        }*/

        getModel().endTurn(currentPlayer);

        while(!getModel().getTurnManager().isTurnEnded()){
            try{
                wait();
            }
            catch(InterruptedException e){

            }
        }

    }
}
