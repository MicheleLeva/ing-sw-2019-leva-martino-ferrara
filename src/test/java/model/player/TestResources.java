package model.player;

import model.Ammo;
import model.Model;
import model.cards.AmmoColor;
import model.cards.powerups.Newton;
import model.cards.powerups.PowerUp;
import model.cards.weapons.LockRifle;
import model.cards.weapons.Weapon;
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


    /**
     * Tests Resources class getters for Ammunition
     */
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

    /**
     * Tests all methods that modify the Ammo in the class Resources
     */
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

    /**
     * Tests getters and setters of current weapons in Resources class
     */
    @Test
    public void testWeapons(){
        Ammo ammo = new Ammo(1,0,0);
        LockRifle lockRifle = new LockRifle("lockrifle",ammo,ammo, ammo, 1,1,
                1,1,1,1, modelTest);

        lockRifle.reload();
        System.out.println(player1.getResources().printUnloadedWeapons());
        lockRifle.unload();
        System.out.println(player1.getResources().printUnloadedWeapons());

        player1.getResources().addWeapon(lockRifle);
        System.out.println(player1.getResources().showWeapon());
        player1.getResources().getAllWeapon().clear();
        System.out.println(player1.getResources().showWeapon());

        lockRifle.reload();
        assertNotNull(player1.getResources().getReloadedWeapon());
    }
}