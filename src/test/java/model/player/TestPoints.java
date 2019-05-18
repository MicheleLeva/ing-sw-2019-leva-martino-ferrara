package model.player;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPoints {

    private Points pointsTest;

    @Before
    public void initPointsTest(){
        pointsTest = new Points();
    }


    @Test @Ignore //todo
    public void testGetPointsList(){
        assertEquals(1,pointsTest.getPointsList().toArray()[0]);
        assertEquals(1,pointsTest.getPointsList().toArray()[1]);
        assertEquals(2,pointsTest.getPointsList().toArray()[2]);
        assertEquals(4,pointsTest.getPointsList().toArray()[3]);
        assertEquals(6,pointsTest.getPointsList().toArray()[4]);
        assertEquals(8,pointsTest.getPointsList().toArray()[5]);

    }


    @Test @Ignore //todo
    public void testPointsRemover(){
        pointsTest.removeHighestPoint();
        pointsTest.removeHighestPoint();
        assertEquals(2,pointsTest.getPointsList().toArray()[0]);
    }
}