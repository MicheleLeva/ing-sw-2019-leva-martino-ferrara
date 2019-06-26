package model.player.action;
import model.Model;
import model.adrenaline_exceptions.WallException;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestRun {
    private Player playerTest;
    private Model modelTest;
    private Action runAction;
    private Map map;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        map = new Map(1);

    }
    @Test
    public void testPerform(){
        Square square = map.getSquareFromCoordinates(0,0);
        playerTest.setPosition(square);
        runAction = new Run(KeyMap.getMoveLeft());
        try{
            runAction.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e) {
            assertNotNull(e.getMessage());
        }

        runAction = new Run(KeyMap.getMoveDown());
        try{
            runAction.perform(modelTest,playerTest.getPlayerColor());
            assertNotEquals(square,playerTest.getPosition());
            ActionTree actionTree = playerTest.getActionTree();
            assertTrue(actionTree.getLastActionPerformed().equals(actionTree.getLastAction()));
        }
        catch(Exception e){

        }

        playerTest.setPosition(square);
        runAction = new Run(KeyMap.getMoveUp());
        try{
            runAction.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e) {
            assertNotNull(e.getMessage());
        }

        runAction = new Run(KeyMap.getMoveRight());
        try{
            runAction.perform(modelTest,playerTest.getPlayerColor());
            assertNotEquals(square,playerTest.getPosition());
            ActionTree actionTree = playerTest.getActionTree();
            assertTrue(actionTree.getLastActionPerformed().equals(actionTree.getLastAction()));
        }
        catch(Exception e){

        }




    }
}
