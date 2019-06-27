package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayerBoard {

    private PlayerBoard playerBoardTest;
    @Before
    public void initPlayerBoardTest(){
        playerBoardTest = new PlayerBoard();
    }

    /**
     * Tests getters for GameBoard class
     */
    @Test
    public void testGetters(){
        assertNotNull(playerBoardTest.getDamageCounter());
        assertNotNull(playerBoardTest.getMarkCounter());
        assertNotNull(playerBoardTest.getPoints());

    }


}