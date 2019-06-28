package model.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSquare {

    private Square squareTest;

    @Before
    public void initSquare(){
        this.squareTest = new Square(2,3);
        squareTest.setID(3);
        squareTest.isSpawn = true;
        squareTest.setColor(SquareColor.BLUE);

        }

    /**
     * Tests the correct creation of a square by checking its color, type, position and weapons/AmmoCards
     */
    @Test
        public void testSquare(){
        assertEquals(SquareColor.BLUE,squareTest.getColor());
        assertEquals(3,squareTest.getSquareColumn());
        assertEquals(2,squareTest.getSquareRow());
        assertEquals("square",squareTest.toString());
        assertEquals(3,squareTest.getID());
        squareTest.removeAmmoCard();
        assertNull(squareTest.getAmmoCard());
        squareTest.setSide(Direction.NORTH,null);
        assertNull(squareTest.getSide(Direction.NORTH));
        assertTrue(squareTest.isEmpty());
        squareTest.getWeapon();
        }
    }

