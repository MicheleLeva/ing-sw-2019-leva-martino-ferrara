package model.player.action;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestActionCreator {
    private KeyMap keyMap;
    @Before
    public void initKeyMap()
    {
        //initializes the keymap used for the correspondence between inputs and actions
        keyMap = new KeyMap();
    }
    @Test
    public void testCreateAction()
    {
        //testing the correspondence bewteen inputs and actions

        //testing shoot
        assertTrue(ActionCreator.createAction(KeyMap.getShoot()) instanceof Shoot);

        //testing Run
        //testing moveUp
        assertTrue(ActionCreator.createAction(KeyMap.getMoveUp()) instanceof Run);

        //testing moveDown
        assertTrue((ActionCreator.createAction((KeyMap.getMoveDown())) instanceof Run));

        //testing moveLeft
        assertTrue((ActionCreator.createAction((KeyMap.getMoveLeft())) instanceof Run));

        //testing moveRight
        assertTrue((ActionCreator.createAction((KeyMap.getMoveRight())) instanceof Run));

        //testing Grab
        assertTrue((ActionCreator.createAction((KeyMap.getGrab())) instanceof Grab));

        //testing usePowerup
        assertTrue((ActionCreator.createAction((KeyMap.getUsePowerUp())) instanceof UsePowerUp));

        //testing end
        assertTrue((ActionCreator.createAction((KeyMap.getEnd())) instanceof EndAction));

        //testing reload
        assertTrue((ActionCreator.createAction((KeyMap.getReload())) instanceof Reload));

        //testing else
        assertTrue(ActionCreator.createAction('L') instanceof EndAction);
    }
}
