package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods of the PlayerBoard class
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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