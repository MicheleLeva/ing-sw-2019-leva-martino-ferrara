package model;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.events.Message;
import model.map_package.Direction;
import model.player_package.Figure;
import model.player_package.Player;
import model.player_package.PlayerColor;
import java.util.HashMap;
import utils.Observable;

public class Model extends Observable<Message> {

    private final HashMap<PlayerColor , Player> players = new HashMap<>();

    private GameBoard gameBoard;

    private final TurnManager turnManager = new TurnManager();

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public Player getPlayer(PlayerColor playerColor){
        return players.get(playerColor);
    }

    public void performRun(PlayerColor playerColor , Direction direction){
        Player player = getPlayer(playerColor);
        Figure figure = player.getFigure();
        player.getFigure().setPosition(player.getFigure().getPosition().enter());
    }

    public void performShoot(PlayerColor shooterColor, PlayerColor opponentColor, Weapon weapon, PowerUp powerUp){

    }

    public void performGrab(PlayerColor playerColor){

    }

}
