import model.adrenaline_exceptions.WallException;
import model.map_package.Wall;
import org.junit.Test;

public class TestWall {
    Wall wallTest;
    @Test(expected = WallException.class)
    public void testWallEnter() throws WallException {
        wallTest = new Wall();
        wallTest.enter();


    }
}
