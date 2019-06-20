package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestTurnManager {

    private Player player1 ;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();
    private TurnManager testTurnManager;

    @Before
    public void initTest(){
        player1 = new Player("player1", PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        player4 = new Player("player4",PlayerColor.PURPLE);
        player5 = new Player("player5",PlayerColor.GREY);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        modelTest = new Model(players, 8);
        testTurnManager = modelTest.getTurnManager();
    }

    @Test
    public void testGetPlayerFromColor(){
        assertEquals(player1, testTurnManager.getPlayerFromColor(PlayerColor.BLUE));
        assertEquals(player2, testTurnManager.getPlayerFromColor(PlayerColor.GREEN));
        assertEquals(player3, testTurnManager.getPlayerFromColor(PlayerColor.YELLOW));
        assertNotEquals(player1, testTurnManager.getPlayerFromColor(PlayerColor.PURPLE));
    }

    @Test
    public void testUpdate(){
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        assertEquals(player1, testTurnManager.getCurrentPlayer());

        modelTest.setPlayerAfk(player2);
        testTurnManager.update();
        assertEquals(player3, testTurnManager.getCurrentPlayer());

        modelTest.setPlayerAfk(player3);
        modelTest.setPlayerAfk(player4);
        testTurnManager.update();
        assertTrue(testTurnManager.isGameOver());

        testTurnManager.setGameOver(false);
        modelTest.wakeUpPlayer(player2);
        modelTest.wakeUpPlayer(player3);
        modelTest.wakeUpPlayer(player4);

        testTurnManager.setFrenzy();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        testTurnManager.update();
        assertTrue(testTurnManager.isGameOver());
    }

}
