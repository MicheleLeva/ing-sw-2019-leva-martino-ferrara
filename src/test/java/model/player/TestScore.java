package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests the methods of the Score class
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TestScore {

    private Score scoreTest;

    @Before
    public void initScore() {
        scoreTest = new Score();
    }

    /**
     * Tests the correct addition of points to a score
     */
    @Test
    public void testAddScore() {
        scoreTest.addScore(3);
        assertEquals(3, scoreTest.getScore());

    }

    /**
     * Tests Score class getters
     */
    @Test
    public void getScore() {
        assertEquals(0, scoreTest.getScore());
        scoreTest.addScore(3);
        assertEquals(3, scoreTest.getScore());

    }
}