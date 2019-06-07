package model;

import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

    @Test
    public void testGameBoardGetters(){
        assertNotNull(gameBoardTest.getMap());
        assertNotNull(gameBoardTest.getKillShotTrack());
        assertNotNull(gameBoardTest.getDecks());

    }

    @Test
    public void testGameBoardActions(){
        gameBoardTest.addToken(PlayerColor.YELLOW);
        assertEquals(PlayerColor.YELLOW, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenColor());
        assertEquals(1, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenNumber());
        assertFalse(gameBoardTest.getKillShotTrack().getKillShotTrack()[0].isSkull());

    }
}
