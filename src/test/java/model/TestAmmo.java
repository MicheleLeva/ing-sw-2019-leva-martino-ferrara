package model;

import model.game.Ammo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods of the Ammo class
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TestAmmo {


    private Ammo ammoTest;

    @Before
    public void initAmmoTest(){
        ammoTest = new Ammo(2,3,1);
    }

    /**
     * Tests Ammo class getters
     */
    @Test
    public void testGetters(){
        assertEquals(2,ammoTest.getRed());
        assertEquals(3,ammoTest.getBlue());
        assertEquals(1,ammoTest.getYellow());

    }


    /**
     * Tests Ammo class getters
     */
    @Test
    public void testSetters(){

        ammoTest.setRed(3);
        ammoTest.setBlue(2);
        ammoTest.setYellow(3);



        assertEquals(3,ammoTest.getRed());
        assertEquals(2,ammoTest.getBlue());
        assertEquals(3,ammoTest.getYellow());




    }


}