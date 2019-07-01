package model.player.action;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestActionTree {

    private ActionTree actionTree;
    private KeyMap keyMap;

    @Before
    public void init(){
        actionTree = new ActionTree(1);
        keyMap = new KeyMap();

    }

    /**
     * Tests action tree initialization
     */
    @Test
    public void testInit(){
        assertEquals(1,actionTree.getID());
        assertEquals(0,actionTree.getPerformedAction());
        assertEquals(actionTree.getLastActionPerformed(),actionTree.getLastAction());
    }

    /**
     * Tests getter
     */
    @Test
    public void testGetRoot(){
        assertNotNull(actionTree.getRoot());
        assertNull(actionTree.getRoot().getParent());
    }

    /**
     * Tests action tree well formation
     */
    @Test
    public void testCheckAction(){
        assertTrue(actionTree.checkAction(KeyMap.getEnd()));
        assertTrue(actionTree.checkAction(KeyMap.getUsePowerUp()));

        assertTrue(actionTree.checkAction(KeyMap.getUsePowerUp()));
        assertTrue(actionTree.checkAction(KeyMap.getMoveUp()));
        assertFalse(actionTree.checkAction(KeyMap.getReload()));

        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.checkAction(KeyMap.getReload()));

    }

    /**
     * Tests action tree walking
     */
    @Test
    public void testUpdateAction(){
        actionTree.updateAction();
        assertEquals(actionTree.getLastActionPerformed(),actionTree.getLastAction());
        assertTrue(actionTree.isMoveEnded());

        int performedAction = actionTree.getPerformedAction();
        actionTree.getLastAction().getChildren().clear();
        actionTree.updateAction();
        assertEquals(actionTree.getPerformedAction(), performedAction + 1);
    }
    @Test
    public void testSetMoveEnded(){
        actionTree.setMoveEnded(true);
        assertTrue(actionTree.isMoveEnded());
        actionTree.setMoveEnded(false);
        assertFalse(actionTree.isMoveEnded());
    }
    @Test
    public void testIsMoveEnded(){
        assertFalse(actionTree.isMoveEnded());
    }
    @Test
    public void testAvailableAction(){
        assertNotNull(actionTree.availableAction());
    }
    @Test
    public void testIsActionEnded(){
        assertFalse(actionTree.isActionEnded());
        actionTree.getLastActionPerformed().getChildren().clear();
        assertTrue(actionTree.isActionEnded());
    }
    @Test
    public void testResetAction(){
        actionTree.resetAction();
        assertEquals(0,actionTree.getPerformedAction());
        assertEquals(actionTree.getLastAction(),actionTree.getLastActionPerformed());
    }
    @Test
    public void testIsTurnEnded(){
        assertFalse(actionTree.isTurnEnded());
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());


    }
    @Test
    public void testHasDoneTurn(){
        assertFalse(actionTree.hasDoneTurn());
        actionTree.setDoneTurn(true);
        assertTrue(actionTree.hasDoneTurn());
    }
    @Test
    public void testEndAction(){
        int performedAction = actionTree.getPerformedAction();
        actionTree.endAction();
        assertEquals(actionTree.getPerformedAction(),performedAction + 1);
        assertTrue(actionTree.isMoveEnded());
        assertEquals(actionTree.getLastAction(),actionTree.getLastActionPerformed());
        assertEquals(actionTree.getLastAction(),actionTree.getRoot());
    }
    @Test
    public void testGetID(){
        assertEquals(1,actionTree.getID());
    }
    @Test
    public void testGetLastAction(){
        assertNotNull(actionTree.getLastAction());
    }
    @Test
    public void testGetLastActionPerformed(){
        assertNotNull(actionTree.getLastActionPerformed());
    }
    @Test
    public void testGetPerformedAction(){
        assertEquals(0,actionTree.getPerformedAction());
    }

    /**
     * Tests tree initialization with different IDs
     */
    @Test
    public void testID(){
        actionTree = new ActionTree(1);
        assertEquals(1,actionTree.getID());
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
        actionTree = new ActionTree(2);
        assertEquals(2,actionTree.getID());
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
        actionTree = new ActionTree(3);
        assertEquals(3,actionTree.getID());
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
        actionTree = new ActionTree(4);
        assertEquals(4,actionTree.getID());
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
        actionTree = new ActionTree(5);
        assertEquals(5,actionTree.getID());
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
    }


}
