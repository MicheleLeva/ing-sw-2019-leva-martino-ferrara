package model;

import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.cards.Weapon;
import model.events.DrawMessage;
import model.events.Message;
import model.events.RunMessage;
import model.events.ShowCardsMessage;
import model.map_package.Direction;
import model.map_package.*;
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

    public void performRun(PlayerColor playerColor, int x, int y){
        Player player = getPlayer(playerColor);
        player.setPosition(gameBoard.getMap().getSquareFromCoordinates(x,y));
        notify(new RunMessage(playerColor, player.getPlayerName(), player.getPosition()));
    }

    public void performShoot(PlayerColor shooterColor, PlayerColor opponentColor, Weapon weapon, PowerUp powerUp) throws IllegalOpponentException {
        //Need weapon cards
        //notify
    }

    public void performGrab(PlayerColor playerColor) throws EmptySquareException {
        Player player = getPlayer(playerColor);

        if (player.getPosition().getAmmoCard() !=null){
            AmmoCard ammoCard = player.getPosition().getAmmoCard();
            //Need ammo cards
        }else throw new EmptySquareException();
        //notify
    }

    public void performReload(PlayerColor playerColor, int index) throws InsufficientAmmoException {
        //Need weapon cards
    }

    public void performDraw(PlayerColor playerColor){
        Player player = getPlayer(playerColor);
        PowerUp drawnCard = gameBoard.getDecks().drawPowerUp();
        player.getResources().addPowerUp(drawnCard);
        notify(new DrawMessage(playerColor, player.getPlayerName(), drawnCard));
    }

    public void performUsePowerUp(PlayerColor playerColor, int index){
        //Need powerup cards
    }

    public void performShowCards(PlayerColor playerColor){
        Player player = getPlayer(playerColor);
        notify(new ShowCardsMessage(playerColor, player.getPlayerName(), player.getResources().showPowerUps(), player.getResources().showWeapons()));
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

        LinkedHashSet<Square> hashSet = new LinkedHashSet<>(squares); //converting to hashset to remove duplicates

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
