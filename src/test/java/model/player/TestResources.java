package model.player;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestResources {

    private Resources resourcesTest;

    @Before
    public void initResourcesTest(){
        resourcesTest = new Resources();
    }

    @Test @Ignore
    public void testAmmoGetters(){
        assertEquals(2,resourcesTest.getAllAmmo().getBlue());
        assertEquals(2,resourcesTest.getAllAmmo().getRed());
        assertEquals(2,resourcesTest.getAllAmmo().getYellow());

        assertEquals(1,resourcesTest.getAvailableAmmo().getBlue());
        assertEquals(1,resourcesTest.getAvailableAmmo().getRed());
        assertEquals(1,resourcesTest.getAvailableAmmo().getYellow());

        assertTrue(resourcesTest.getAllAmmo()!=null);
        assertTrue(resourcesTest.getAvailableAmmo()!=null);

    }

    @Test @Ignore //todo, fallisce
    public void testAmmoModifiers(){

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