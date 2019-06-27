package model.map;

import model.Ammo;
import model.Model;
import model.cards.AmmoCard;
import model.cards.weapons.Heatseeker;
import model.cards.weapons.LockRifle;
import model.cards.weapons.Weapon;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSquare {

    Square squareTest;

    @Before
    public void initSquare(){
        Ammo ammo = new Ammo(0,0,0);
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
        assertEquals(squareTest.getColor(),SquareColor.BLUE);
        assertEquals(squareTest.getSquareColumn(),3);
        assertEquals(squareTest.getSquareRow(),2);
        assertEquals(squareTest.toString(),"square");
        assertEquals(squareTest.getID(),3);
        squareTest.removeAmmoCard();
        assertNull(squareTest.getAmmoCard());
        squareTest.setSide(Direction.NORTH,null);
        assertNull(squareTest.getSide(Direction.NORTH));
        assertTrue(squareTest.isEmpty());
        squareTest.getWeapon();
        }
    }

