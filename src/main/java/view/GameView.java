package view;

import model.player_package.PlayerColor;
import utils.update.GameUpdate;

public class GameView implements GameUpdate {
    private final PlayerColor playerColor;

    public GameView(PlayerColor playerColor){
        this.playerColor = playerColor;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }
}
