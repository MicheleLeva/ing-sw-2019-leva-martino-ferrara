package model;
import model.cards.AmmoColor;
import model.game.CLI;
import model.map.SquareColor;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests the methods of the CLI class
 * @author Stefano Martino
 */
public class TestCLI {
    /**
     * Test getters
     */
    @Test
    public void testGetColor(){
        assertEquals(CLI.getBlue(),CLI.getColor(PlayerColor.BLUE));
        assertEquals("\u001B[32m",CLI.getColor(PlayerColor.GREEN));
        assertEquals("\u001B[35m",CLI.getColor(PlayerColor.PURPLE));
        assertEquals(CLI.getYellow(),CLI.getColor(PlayerColor.YELLOW));
        assertEquals("\u001B[37m",CLI.getColor(PlayerColor.GREY));

        assertEquals(CLI.getBlue(),CLI.getColor(AmmoColor.BLUE));
        assertEquals(CLI.getRed(),CLI.getColor(AmmoColor.RED));
        assertEquals(CLI.getYellow(),CLI.getColor(AmmoColor.YELLOW));

        assertEquals(CLI.getBlue(),CLI.getColor(SquareColor.BLUE));
        assertEquals(CLI.getRed(), CLI.getColor(SquareColor.RED));
        assertEquals("\u001B[32m", CLI.getColor(SquareColor.GREEN));
        assertEquals(CLI.getYellow(), CLI.getColor(SquareColor.YELLOW));
        assertEquals("\u001B[35m",CLI.getColor(SquareColor.PURPLE));
        assertEquals("",CLI.getColor(SquareColor.WHITE));

    }
    /**
     * Test getters
     */
    @Test
    public void testGetSkull(){
        assertNotNull(CLI.getSkull());
    }
    /**
     * Test getters
     */
    @Test
    public void testGetResetString(){
        assertNotNull(CLI.getResetString());
        assertEquals("\u001B[0m",CLI.getResetString());
    }
    /**
     * Test getters
     */
    @Test
    public void testGedDamage(){
        assertEquals("*",CLI.getDamage());
    }
    /**
     * Test getters
     */
    @Test
    public void testGetBlue(){
        assertEquals("\u001B[34m",CLI.getBlue());
    }
    /**
     * Test getters
     */
    @Test
    public void testGetRed(){
        assertEquals("\u001B[31m",CLI.getRed());

    }
    /**
     * Test getters
     */
    @Test
    public void testGetYellow(){
        assertEquals("\u001B[33m",CLI.getYellow());
    }
    /**
     * Test getters
     */
    @Test
    public void testColorMap(){
        CLI cli = new CLI();
        String result = null;
        try{
            result = cli.buildCLIMap("src/resources/map1.txt");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        assertNotNull(result);
    }

}
