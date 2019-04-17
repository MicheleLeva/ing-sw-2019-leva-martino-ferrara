import model.map_package.Door;
import model.map_package.Square;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDoor {

    Door doorTest;
    Square nextSquare;
    @Before
    public void squareInit() {
        nextSquare = new Square();
        doorTest = new Door(nextSquare);
    }

    @Test
    public void testDoorEnter(){
        assertEquals(doorTest.enter(),nextSquare);

    }
}
