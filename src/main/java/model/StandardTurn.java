package model;

import model.player_package.Player;
import model.player_package.PlayerColor;
import model.player_package.action.KeyMap;

public class StandardTurn extends Turn {

    public StandardTurn(Model model){
        super(model);
    }

    @Override
    public void startTurn() {
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        //Mostra al giocatore la lista comandi
        getModel().printMessage(currentPlayerColor, KeyMap.getCommandList(), "");

        while (!currentPlayer.getActionTree().isEnd()) {
            //Mostra al giocatore le azioni disponibili
            getModel().printMessage(currentPlayerColor, currentPlayer.getActionTree().availableAction(), "");
            //Chiedi input
            getModel().askTurnInput();
            //Verifica input:
            //1: input = show cards -> mostra carte
            //2: input = usepowerUp -> verifica se ha powerUp, se può pagarli e se è il suo turno
            //3: input == azione -> verifica se è valida e se può farla


        }
    }
}
