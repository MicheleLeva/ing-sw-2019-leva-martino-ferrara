package model;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.events.Message;
import model.map_package.Direction;
import model.map_package.*;
import model.player_package.Figure;
import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

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

    public void performRun(PlayerColor playerColor){
        Player player = getPlayer(playerColor);
        player.getFigure().setPosition(player.getFigure().getPosition().enter());
    }

    public void performShoot(PlayerColor shooterColor, PlayerColor opponentColor, Weapon weapon, PowerUp powerUp){

    }

    public void performGrab(PlayerColor playerColor){

    }

    public Model(ArrayList<Player> playersList, int skulls){

        players.put(PlayerColor.BLUE, playersList.get(0));
        players.put(PlayerColor.GREEN, playersList.get(1));
        players.put(PlayerColor.PURPLE, playersList.get(2));
        if (playersList.size()== 4){
            players.put(PlayerColor.YELLOW, playersList.get(3));
        }
        if (playersList.size()== 5){
            players.put(PlayerColor.GREY, playersList.get(4));
        }

        gameBoard = new GameBoard(playersList.size(), skulls);
    }

    public static ArrayList<Square> runnableSquare(int n, Square startingSquare){

        ArrayList<Square> squares = new ArrayList<>();

        squares.add(startingSquare);

        if (n>0){
            if (getSquareFromDir(startingSquare, Direction.NORTH) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.NORTH)));
            }
            if (getSquareFromDir(startingSquare, Direction.SOUTH) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.SOUTH)));
            }
            if (getSquareFromDir(startingSquare, Direction.EAST) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.EAST)));
            }
            if (getSquareFromDir(startingSquare, Direction.WEST) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.WEST)));
            }
        }

        LinkedHashSet<Square> hashSet = new LinkedHashSet<>(squares);

        return new ArrayList<>(hashSet);

    }

    private static Square getSquareFromDir(Square square, Direction direction){

        MapElement side = square.getSide(direction);

        if (side instanceof Door) {
            return ((Door) side).enter();
        }
        if (side instanceof  Square){
            return (Square) side;
        }
        return null;
    }

}
