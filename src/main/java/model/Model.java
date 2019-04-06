package model;

import model.Cards.PowerUp;
import model.Cards.Weapon;
import model.events.Message;
import model.map_package.Direction;
import model.player_package.Player;
import model.player_package.PlayerColor;
import java.util.HashMap;
import utils.Observable;

public class Model extends Observable<Message> {

    private final HashMap<PlayerColor , Player> players = new HashMap<PlayerColor , Player>();

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
        player.getFigure().setPosition(player.getFigure().getPosition().enter());
    }

    public void performShoot(PlayerColor shooterColor, PlayerColor opponentColor, Weapon weapon, PowerUp powerUp){

    }

    public void performGrab(PlayerColor playerColor){

    }

}
