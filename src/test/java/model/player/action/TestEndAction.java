package model.player.action;
import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestEndAction {
    private Player playerTest;
    private Model modelTest;
    private Action endAction;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        endAction = new EndAction();
    }
    @Test
    public void testPerform(){
        try{
            int performedAction = playerTest.getActionTree().getPerformedAction();
            endAction.perform(modelTest,playerTest.getPlayerColor());
            assertEquals(playerTest.getActionTree().getPerformedAction(), performedAction + 1);
            assertTrue(playerTest.getActionTree().isMoveEnded());
            assertEquals(playerTest.getActionTree().getLastActionPerformed(), playerTest.getActionTree().getLastAction());

            ActionTree actionTree = playerTest.getActionTree();
            assertTrue(actionTree.checkAction(KeyMap.getMoveUp()));
            actionTree.updateAction();
            assertTrue(actionTree.checkAction(KeyMap.getMoveUp()));
            actionTree.updateAction();
            assertTrue(actionTree.checkAction(KeyMap.getMoveUp()));
            actionTree.updateAction();
            assertTrue(actionTree.isTurnEnded());

            endAction.perform(modelTest,playerTest.getPlayerColor());
            assertTrue(actionTree.hasDoneTurn());
            assertTrue(actionTree.isMoveEnded());

        }
        catch(Exception e ){
            e.printStackTrace();
        }

    }
}

