package model.map_package;

import model.map_package.Door;
import model.map_package.Square;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDoor {

    private Door doorTest;
    private Square nextSquare;
    @Before
    public void squareInit() {
        int row = 1;
        int column = 3;
        nextSquare = new Square(row, column);
        doorTest = new Door(nextSquare);
    }

    @Test
    public void testDoorEnter(){
        assertEquals(doorTest.enter(),nextSquare);

    }
}
