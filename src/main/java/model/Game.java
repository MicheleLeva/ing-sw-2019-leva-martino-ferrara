package model;

import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final int gameID;

    private Model model = null;

    private Turn currentTurn;

    public Game(int gameID, List<String> playerNames){
        this.gameID = gameID;
        init(playerNames);
    }

    private void init(List<String> names){ //Inizializzazione, creazione model, passando lista giocatori.

        ArrayList<Player> players = new ArrayList<>();

            players.add(new Player(names.get(0), PlayerColor.BLUE));
            players.add(new Player(names.get(1), PlayerColor.GREEN));
            players.add(new Player(names.get(2), PlayerColor.PURPLE));
            if (names.size()== 4){
                players.add(new Player(names.get(3), PlayerColor.YELLOW));
            }
            if (names.size()== 5){
                players.add(new Player(names.get(4), PlayerColor.GREY));
            }

        this.model = new Model(players, 8);

        startGame();
    }

    private void startGame(){
        while(!model.getTurnManager().isGameOver()){
            currentTurn = model.getCurrentTurn().getTurn();
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            model.getTurnManager().update();
        }


        //Calcolo punteggi
        //model.endGame();
    }

}
