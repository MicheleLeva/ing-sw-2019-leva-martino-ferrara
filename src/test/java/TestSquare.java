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
    Square squareTest;
    @Before
    public void initSquare(){
        squareTest = new Square();
        squareTest.isSpawn = true;
        squareTest.setColor(SquareColor.YELLOW);
        squareTest.setSide(Direction.NORTH, new Door(new Square()));
        squareTest.setSide(Direction.SOUTH, new Wall());
        squareTest.setSide(Direction.EAST, new Square());
        squareTest.setSide(Direction.WEST, new Door(new Square()));
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
