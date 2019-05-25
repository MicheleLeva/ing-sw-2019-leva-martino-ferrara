package model;

import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameBoard {

    private GameBoard gameBoardTest;


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

    @Test @Ignore //todo
    public void testGameBoardActions(){
        gameBoardTest.addToken(PlayerColor.YELLOW);
        assertEquals(PlayerColor.YELLOW, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenColor());
        assertEquals(2, gameBoardTest.getKillShotTrack().getKillShotTrack()[0].getTokenNumber());
        assertFalse(gameBoardTest.getKillShotTrack().getKillShotTrack()[0].isSkull());

    }
}
