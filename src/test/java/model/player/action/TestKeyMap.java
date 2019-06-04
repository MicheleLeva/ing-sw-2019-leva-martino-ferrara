package model.player.action;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestKeyMap {
    private KeyMap keyMap;
    @Before
    public void initKeyMap()
    {
        keyMap = new KeyMap();
    }
    @Test
    public void testIsReload()
    {
        assertEquals('R',KeyMap.getReload());
    }
    @Test
    public void testIsRunUp()
    {
        assertEquals('W',KeyMap.getMoveUp());
    }
    @Test
    public void testIsRunDown()
    {
        assertEquals('S',KeyMap.getMoveDown());
    }
    @Test
    public void testIsRunLeft()
    {
        assertEquals('A',KeyMap.getMoveLeft());
    }
    @Test
    public void testIsRunRight()
    {
        assertEquals('D',KeyMap.getMoveRight());
    }
    @Test
    public void testIsGrab()
    {
        assertEquals('E',KeyMap.getGrab());
    }
    @Test
    public void testIsUsePowerUp()
    {
        assertEquals('U',KeyMap.getUsePowerUp());
    }
    @Test
    public void testIsEnd()
    {
        assertEquals('Z',KeyMap.getEnd());
    }
    @Test
    public void testIsShoot()
    {
        assertEquals('F',KeyMap.getShoot());
    }

}
