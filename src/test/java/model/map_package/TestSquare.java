package model.map_package;

import model.map_package.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;




public class TestSquare {
    private Square squareTest;

    @Before
    public void initSquare(){
        int row = 2;
        int column = 3;
        squareTest = new Square(row, column);
        squareTest.isSpawn = true;
        squareTest.setColor(SquareColor.YELLOW);
        squareTest.setSide(Direction.NORTH, new Door(new Square(row-1,column)));
        squareTest.setSide(Direction.SOUTH, new Wall());
        squareTest.setSide(Direction.EAST, new Square(row, column+1));
        squareTest.setSide(Direction.WEST, new Door(new Square(row,column-1)));
    }


    @Test
    public void testSquareGetters(){
        assertTrue(squareTest.getSide(Direction.NORTH) instanceof Door);
        assertTrue(squareTest.getSide(Direction.SOUTH) instanceof Wall);
        assertTrue(squareTest.getSide(Direction.EAST) instanceof Square);
        assertTrue(squareTest.getSide(Direction.WEST) instanceof Door);
        assertTrue(squareTest.isSpawn);
        assertEquals("YELLOW",squareTest.getColor().toString());
    }


}
