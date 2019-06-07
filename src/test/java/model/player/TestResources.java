package model.player;

import model.Model;
import model.cards.AmmoColor;
import model.cards.powerups.Newton;
import model.cards.powerups.PowerUp;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestResources {

    private Resources resourcesTest;
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();
    private Newton newtonRed;
    private Newton newtonBlue;
    private Newton newtonYellow;

    @Before
    public void initResourcesTest(){
        resourcesTest = new Resources();

        player1 = new Player("player1",PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        newtonRed = new Newton(modelTest, AmmoColor.RED);
        newtonBlue = new Newton(modelTest, AmmoColor.BLUE);
        newtonYellow = new Newton(modelTest, AmmoColor.YELLOW);
    }


    @Test
    public void testAmmoGetters(){

        assertEquals(1,resourcesTest.getAllAmmo().getBlue());
        assertEquals(1,resourcesTest.getAllAmmo().getRed());
        assertEquals(1,resourcesTest.getAllAmmo().getYellow());

        assertEquals(1,resourcesTest.getAvailableAmmo().getBlue());
        assertEquals(1,resourcesTest.getAvailableAmmo().getRed());
        assertEquals(1,resourcesTest.getAvailableAmmo().getYellow());

        assertNotNull(resourcesTest.getAllAmmo());
        assertNotNull(resourcesTest.getAvailableAmmo());

    }

    @Test
    public void testAmmoModifiers(){

        ArrayList<PowerUp> powerUps= new ArrayList<>();
        powerUps.add(newtonRed);
        powerUps.add(newtonBlue);
        powerUps.add(newtonYellow);

        resourcesTest.addPowerUp(powerUps);
        resourcesTest.addToAvailableAmmo(1,1,1);

        assertEquals(3,resourcesTest.getAllAmmo().getBlue());
        assertEquals(3,resourcesTest.getAllAmmo().getRed());
        assertEquals(3,resourcesTest.getAllAmmo().getYellow());

        assertEquals(2,resourcesTest.getAvailableAmmo().getBlue());
        assertEquals(2,resourcesTest.getAvailableAmmo().getRed());
        assertEquals(2,resourcesTest.getAvailableAmmo().getYellow());

        resourcesTest.removeFromAvailableAmmo(2,2,2);

        assertEquals(1,resourcesTest.getAllAmmo().getBlue());
        assertEquals(1,resourcesTest.getAllAmmo().getRed());
        assertEquals(1,resourcesTest.getAllAmmo().getYellow());

        assertEquals(0,resourcesTest.getAvailableAmmo().getBlue());
        assertEquals(0,resourcesTest.getAvailableAmmo().getRed());
        assertEquals(0,resourcesTest.getAvailableAmmo().getYellow());

    }
}