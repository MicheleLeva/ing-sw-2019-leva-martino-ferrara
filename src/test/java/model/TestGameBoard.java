package model;

import model.GameBoard;
import model.player_package.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameBoard {

    GameBoard gameBoardTest;


    @Before
    public void initGameBoard(){
        gameBoardTest = new GameBoard(3,8);
    }

    @Test
    public void testGameBoardGetters(){
        assertNotNull(gameBoardTest.getMap());
        assertNotNull(gameBoardTest.getKillShotTrack());
        assertNotNull(gameBoardTest.getDecks());

    }

    @Test
    public void testGameBoardActions(){
        gameBoardTest.addToken(PlayerColor.YELLOW, 2);
        assertEquals(PlayerColor.YELLOW, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenColor());
        assertEquals(2, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenNumber());
        assertFalse(gameBoardTest.getKillShotTrack().getKillShotTrack()[0].isSkull());

    }
}
