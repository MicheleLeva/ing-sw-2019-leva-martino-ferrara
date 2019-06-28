package model;

import model.player.DamageCounter;
import model.player.Player;
import model.player.PlayerColor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
public class TestScoreManager {
    ScoreManager scoreManagerTest;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Model modelTest;
    ArrayList<Player> players = new ArrayList<>();
    @Before
    public void initScoreManager(){
        player1 = new Player("player1", PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.YELLOW);
        player3 = new Player("player3",PlayerColor.GREEN);
        player4 = new Player("player4",PlayerColor.PURPLE);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        modelTest = new Model(players,8);
        modelTest.setGameBoard(1);

        scoreManagerTest = new ScoreManager(modelTest);


    }

    @Before
    public void initDamage(){
        //add damage to player1
        //222223334444
        modelTest.addDamage(player2.getPlayerColor(),player1.getPlayerColor(),5);
        modelTest.addDamage(player3.getPlayerColor(),player1.getPlayerColor(),3);
        modelTest.addDamage(player4.getPlayerColor(),player1.getPlayerColor(),4);
    }

    @Test
    public void testShowPlayerRank(){
        String result;
        result = scoreManagerTest.showPlayerRank();
        assertNotNull(result);
    }

    /**
     * Tests that the ScoreManager class is able to determine the winner by updating the score
     */
    @Test
    public void testUpdateScore(){
        scoreManagerTest.updateScore();
        assertEquals(player2.getPlayerColor(),scoreManagerTest.getWinner()); }

    /**
     * Tests that the ScoreManager class is able to determine the winner by updating the score
     * during the frenzy Turn
     */
    @Test
    public void testFinalScore(){
        modelTest.getTurnManager().setFrenzy();
        scoreManagerTest.finalScore();
        assertEquals(player2.getPlayerColor(),scoreManagerTest.getWinner());

    }

}
