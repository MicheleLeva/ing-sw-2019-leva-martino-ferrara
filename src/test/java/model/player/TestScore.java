package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestScore {

    private Score scoreTest;

    @Before
    public void initScore() {
        scoreTest = new Score();
    }

    @Test
    public void testAddScore() {
        scoreTest.addScore(3);
        assertEquals(3, scoreTest.getScore());

    }

    @Test
    public void getScore() {
        assertEquals(0, scoreTest.getScore());
        scoreTest.addScore(3);
        assertEquals(3, scoreTest.getScore());

    }
}