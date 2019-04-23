package model.player_package;

import model.map_package.Square;
import org.junit.Before;
import org.junit.Test;

import static model.player_package.PlayerColor.YELLOW;
import static org.junit.Assert.*;

public class TestPlayer {

    private Player playerTest;

    @Before
    public void initPlayerTest(){
        playerTest = new Player("giocatore", YELLOW);

    }

    @Test
    public void testGetters() {
        assertNotNull(playerTest.getScore());
        assertNotNull(playerTest.getPlayerBoard());
        assertNotNull(playerTest.getActionBoard());
        assertNotNull(playerTest.getResources());
        assertEquals(PlayerColor.YELLOW, playerTest.getPlayerColor());
        assertEquals("giocatore", playerTest.getName());
    }

    @Test
    public void testModifiers() {
        playerTest.addScore(3);
        assertEquals(3,playerTest.getScore().getScore());
        int row = 1;
        int column = 2;
        Square square = new Square(row, column);
        playerTest.setPosition(square);
        assertEquals(square,playerTest.getPosition());


    }

    @Test
    public void getPlayerBoard() {
        assertNotNull(playerTest.getPlayerBoard());
    }
}