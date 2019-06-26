package model.player.action;

import org.junit.Before;
import org.junit.Test;

import javax.swing.text.Keymap;
import java.security.Key;

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
        assertTrue(KeyMap.isReload(KeyMap.getReload()));
    }
    @Test
    public void testIsRunUp()
    {
        assertEquals('W',KeyMap.getMoveUp());
        assertTrue(KeyMap.isRunUp(KeyMap.getMoveUp()));
    }
    @Test
    public void testIsRunDown()
    {
        assertEquals('S',KeyMap.getMoveDown());
        assertTrue(KeyMap.isRunDown(KeyMap.getMoveDown()));
    }
    @Test
    public void testIsRunLeft()
    {
        assertEquals('A',KeyMap.getMoveLeft());
        assertTrue(KeyMap.isRunLeft(KeyMap.getMoveLeft()));
    }
    @Test
    public void testIsRunRight()
    {
        assertEquals('D',KeyMap.getMoveRight());
        assertTrue(KeyMap.isRunRight(KeyMap.getMoveRight()));
    }
    @Test
    public void testIsGrab()
    {
        assertEquals('E',KeyMap.getGrab());
        assertTrue(KeyMap.isGrab(KeyMap.getGrab()));
    }
    @Test
    public void testIsUsePowerUp()
    {
        assertEquals('U',KeyMap.getUsePowerUp());
        assertTrue(KeyMap.isUsePowerUp(KeyMap.getUsePowerUp()));
    }
    @Test
    public void testIsEnd()
    {
        assertEquals('Z',KeyMap.getEnd());
        assertTrue(KeyMap.isEnd(KeyMap.getEnd()));
    }
    @Test
    public void testIsShoot()
    {
        assertEquals('F',KeyMap.getShoot());
        assertTrue(KeyMap.isShoot(KeyMap.getShoot()));
    }
    @Test
    public void testIsValid(){
        assertTrue(KeyMap.isValid("run",KeyMap.getMoveUp()));
        assertTrue(KeyMap.isValid("run",KeyMap.getMoveDown()));
        assertTrue(KeyMap.isValid("run",KeyMap.getMoveLeft()));
        assertTrue(KeyMap.isValid("run",KeyMap.getMoveRight()));

        assertTrue(KeyMap.isValid("grab",KeyMap.getGrab()));
        assertTrue(KeyMap.isValid("shoot",KeyMap.getShoot()));
        assertTrue(KeyMap.isValid("reload",KeyMap.getReload()));

        assertFalse(KeyMap.isValid("run",KeyMap.getShoot()));
        assertFalse(KeyMap.isValid("none",'L'));


    }
    @Test
    public void testIsValid2(){
        assertTrue(KeyMap.isValid(KeyMap.getMoveUp()));
        assertTrue(KeyMap.isValid(KeyMap.getMoveDown()));
        assertTrue(KeyMap.isValid(KeyMap.getMoveLeft()));
        assertTrue(KeyMap.isValid(KeyMap.getMoveRight()));
        assertTrue(KeyMap.isValid(KeyMap.getShoot()));
        assertTrue(KeyMap.isValid(KeyMap.getGrab()));
        assertTrue(KeyMap.isValid(KeyMap.getReload()));
        assertTrue(KeyMap.isValid(KeyMap.getUsePowerUp()));
        assertTrue(KeyMap.isValid(KeyMap.getEnd()));

        assertFalse(KeyMap.isValid('O'));
    }
    @Test
    public void testGetters(){
        assertNotNull(KeyMap.getMoveUp());
        assertNotNull(KeyMap.getMoveDown());
        assertNotNull(KeyMap.getMoveLeft());
        assertNotNull(KeyMap.getMoveRight());
        assertNotNull(KeyMap.getShoot());
        assertNotNull(KeyMap.getGrab());
        assertNotNull(KeyMap.getReload());
        assertNotNull(KeyMap.getUsePowerUp());
        assertNotNull(KeyMap.getEnd());
    }
    @Test
    public void testCommandList(){
        assertNotNull(KeyMap.getCommandList());
    }
    @Test
    public void testIsRun(){
        assertTrue(KeyMap.isRun(KeyMap.getMoveRight()));
        assertTrue(KeyMap.isRun(KeyMap.getMoveLeft()));
        assertTrue(KeyMap.isRun(KeyMap.getMoveDown()));
        assertTrue(KeyMap.isRun(KeyMap.getMoveUp()));
        assertFalse(KeyMap.isRun(KeyMap.getShoot()));


    }
    @Test
    public void testConstructor(){
        keyMap = new KeyMap("non existent path");

    }

}
