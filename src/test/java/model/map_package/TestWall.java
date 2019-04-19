package model.map_package;

import model.adrenaline_exceptions.WallException;
import model.map_package.Wall;
import org.junit.Test;

public class TestWall {
    private Wall wallTest;

    @Test(expected = WallException.class)
    public void testWallEnter() throws WallException {
        wallTest = new Wall();
        wallTest.enter();


    }
}
