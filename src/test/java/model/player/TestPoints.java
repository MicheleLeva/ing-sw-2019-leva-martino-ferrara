package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPoints {

    private Points pointsTest;

    @Before
    public void initPointsTest(){
        pointsTest = new Points();
    }


    /**
     * Tests getters for Points class
     */
    @Test
    public void testGetPointsList(){
        for(int i = 0 ; i< pointsTest.getPointsList().size();i++)
        assertEquals(8,(int)pointsTest.getPointsList().get(0));
        assertEquals(6,(int)pointsTest.getPointsList().get(1));
        assertEquals(4,(int)pointsTest.getPointsList().get(2));
        assertEquals(2,(int)pointsTest.getPointsList().get(3));
        assertEquals(1,(int)pointsTest.getPointsList().get(4));

    }


    /**
     * Tests the removal of the first point in Points class
     */
    @Test
    public void testPointsRemover(){
        pointsTest.removeHighestPoint();
        pointsTest.removeHighestPoint();
        assertEquals(4,(int)pointsTest.getPointsList().get(0));
    }
}