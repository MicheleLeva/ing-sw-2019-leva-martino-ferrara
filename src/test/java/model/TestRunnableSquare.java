package model;

import model.map.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static model.Model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestRunnableSquare {

    private Map map;
    private Square firstSquare;

    @Before
    public void initMap(){
        map = new Map(3);
        firstSquare = map.getSquareFromCoordinates(0, 0);

    }
    @Test
    public void testRunnableSquare(){

        ArrayList<Square> test = new ArrayList<>();

        test.add(firstSquare);
        assertEquals(runnableSquare(0, firstSquare), test);

        test.add(map.getSquareFromCoordinates(0,1));
        test.add(map.getSquareFromCoordinates(1,0));
        assertTrue(test.containsAll(runnableSquare(1,firstSquare)));
        assertEquals(test.size(), runnableSquare(1,firstSquare).size());

        test.add(map.getSquareFromCoordinates(0, 2));
        test.add(map.getSquareFromCoordinates(1, 1));
        assertTrue(test.containsAll(runnableSquare(2, firstSquare)));
        assertEquals(test.size(), runnableSquare(2, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1, 2));
        test.add(map.getSquareFromCoordinates(2, 1));
        assertTrue(test.containsAll(runnableSquare(3, firstSquare)));
        assertEquals(test.size(), runnableSquare(3, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1,3));
        test.add(map.getSquareFromCoordinates(2,2));
        assertTrue(test.containsAll(runnableSquare(4, firstSquare)));
        assertEquals(test.size(), runnableSquare(4, firstSquare).size());
    }


}
