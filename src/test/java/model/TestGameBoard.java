package model;

import model.game.GameBoard;
import model.game.Model;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the methods of the GameBoard class
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TestGameBoard {

    private GameBoard gameBoardTest;
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();


    @Before
    public void initGameBoard(){
        player1 = new Player("player1",PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        gameBoardTest = new GameBoard(3,8, modelTest);
    }

    /**
     * Tests GameBoard class getters
     */
    @Test
    public void testGameBoardGetters(){
        assertNotNull(gameBoardTest.getMap());
        assertNotNull(gameBoardTest.getKillShotTrack());
        assertNotNull(gameBoardTest.getDecks());

    }

    /**
     * Tests that the GameBoard class is able to access the killShotTrack correctly
     */
    @Test
    public void testGameBoardActions(){
        gameBoardTest.addToken(PlayerColor.YELLOW);
        assertEquals(PlayerColor.YELLOW, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenColor());
        assertEquals(1, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenNumber());
        assertFalse(gameBoardTest.getKillShotTrack().getKillShotTrack()[0].isSkull());

    }
}
