package model.player.action;

import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class TestActionTree {

    private ActionTree actionTree;

    @Before
    public void initActionTree(){
        actionTree = new ActionTree(1);
    }
    @Test
    public void testParseActionTree(){
        actionTree = new ActionTree(1);
        //test the number of children


        List<Node<String>> first = actionTree.getRoot().getChildren();
        assertEquals(3,first.size());
        assertEquals("shoot",first.get(0).getData());
        assertEquals("grab",first.get(1).getData());
        assertEquals("run",first.get(2).getData());

        assertTrue(first.get(0).getChildren().isEmpty());
        assertTrue(first.get(1).getChildren().isEmpty());

        List<Node<String>> second = first.get(2).getChildren();
        assertEquals(2,second.size());
        assertEquals("grab",second.get(0).getData());
        assertEquals("run",second.get(1).getData());

        assertTrue(second.get(0).getChildren().isEmpty());

        List<Node<String>> third = second.get(1).getChildren();
        assertEquals(1,third.size());
        assertEquals("run",third.get(0).getData());




    }

    @Test
    public void testActionTree(){
        actionTree = new ActionTree(5);
        assertEquals(5,actionTree.getID());
        assertFalse(actionTree.isMoveEnded());
        assertFalse(actionTree.isTurnEnded());
        assertFalse(actionTree.isActionEnded());
        assertNotNull(actionTree.availableAction());
    }

    @Test
    public void testGetRoot(){
        Node<String> root = actionTree.getRoot();
        assertNotNull(root);
        assertNull(root.getParent());
    }

    @Test
    public void testCheckAction(){
        assertTrue(actionTree.checkAction(KeyMap.getEnd()));
        assertTrue(actionTree.checkAction(KeyMap.getUsePowerUp()));
        assertTrue(actionTree.checkAction(KeyMap.getMoveDown()));
        assertFalse(actionTree.checkAction('L'));
        actionTree.endAction();
        assertTrue(actionTree.checkAction(KeyMap.getReload()));
    }

    @Test
    public void testUpdateAction(){
        assertTrue(actionTree.getLastAction().equals(actionTree.getRoot()));
        assertTrue(actionTree.getLastActionPerformed().equals(actionTree.getRoot()));
        assertFalse(actionTree.checkAction('L'));
        assertTrue(actionTree.getLastAction().equals(actionTree.getRoot()));
        assertTrue(actionTree.getLastActionPerformed().equals(actionTree.getRoot()));
        assertTrue(actionTree.checkAction(KeyMap.getMoveUp()));
        actionTree.updateAction();
        assertTrue(actionTree.isMoveEnded());
        assertTrue(actionTree.getLastActionPerformed().equals(actionTree.getLastAction()));


    }
    @Test
    public void testResetAction(){
        actionTree.resetAction();
        assertFalse(actionTree.isMoveEnded());
        assertFalse(actionTree.isTurnEnded());
        assertFalse(actionTree.isActionEnded());
        assertNotNull(actionTree.availableAction());

    }
    @Test
    public void testSetMoveEnded(){
        actionTree.setMoveEnded(true);
        assertTrue(actionTree.isMoveEnded());
        actionTree.setMoveEnded(false);
        assertFalse(actionTree.isMoveEnded());
    }
    @Test
    public void testEndAction(){
        actionTree.endAction();
        actionTree.endAction();
        assertTrue(actionTree.isTurnEnded());
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
    public void testAvailableAction(){
        assertNotNull(actionTree.availableAction());
    }



}
