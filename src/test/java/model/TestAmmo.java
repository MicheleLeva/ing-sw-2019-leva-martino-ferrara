package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAmmo {


    private Ammo ammoTest;

    @Before
    public void initAmmoTest(){
        ammoTest = new Ammo(2,3,1);
    }


    @Test
    public void testGetters(){
        assertEquals(2,ammoTest.getRed());
        assertEquals(3,ammoTest.getBlue());
        assertEquals(1,ammoTest.getYellow());

    }


    @Test
    public void testSetters(){
        /*
        ammoTest.setRed(3);
        ammoTest.setBlue(2);
        ammoTest.setYellow(3);

        assertEquals(5,ammoTest.getRed());
        assertEquals(5,ammoTest.getBlue());
        assertEquals(4,ammoTest.getYellow());
        */

    }


}