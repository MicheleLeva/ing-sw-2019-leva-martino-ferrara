package model.player;

import model.map.Square;
import org.junit.Before;
import org.junit.Test;

import static model.player.PlayerColor.YELLOW;
import static org.junit.Assert.*;

public class TestPlayer {

    private Player playerTest;

    @Before
    public void initPlayerTest(){
        playerTest = new Player("player", YELLOW);

    }

    /**
     * Tests getters for Player class
     */
    @Test
    public void testGetters() {
        assertNotNull(playerTest.getScore());
        assertNotNull(playerTest.getPlayerBoard());
        assertNotNull(playerTest.getResources());
        assertEquals(PlayerColor.YELLOW, playerTest.getPlayerColor());
        assertEquals("player", playerTest.getPlayerName());
    }

    /**
     * Tests all methods that modify the player's position or state( dead or alive )
     */
    @Test
    public void testModifiers() {
        playerTest.addScore(3);
        assertEquals(3,playerTest.getScore().getScore());
        int row = 1;
        int column = 2;
        Square square = new Square(row, column);
        playerTest.setPosition(square);
        assertEquals(square,playerTest.getPosition());

        playerTest.setDead();
        assertTrue(playerTest.isDead());
        assertNull(playerTest.getPosition());

        playerTest.setAlive();
        assertFalse(playerTest.isDead());
        assertFalse(playerTest.isKillShot());
        assertEquals(0, playerTest.getPlayerBoard().getDamageCounter().getDamage());

    }

    @Test
    public void getPlayerBoard() {
        assertNotNull(playerTest.getPlayerBoard());
        System.out.println(playerTest.printPlayerInfo());
    }


}