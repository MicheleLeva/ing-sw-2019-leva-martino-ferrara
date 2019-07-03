package model;

import model.game.KillShotCell;
import model.game.Model;
import model.game.ScoreManager;
import model.player.Player;
import model.player.PlayerColor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
public class TestScoreManager {
    private ScoreManager scoreManagerTest;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();

    @Before
    public void initScoreManager(){
        player1 = new Player("Stefano", PlayerColor.BLUE);
        player2 = new Player("Marco",PlayerColor.YELLOW);
        player3 = new Player("Michele",PlayerColor.GREEN);
        player4 = new Player("player4",PlayerColor.PURPLE);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        modelTest = new Model(players,1);
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

    /**
     * Test player rank displaying
     */
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
        assertNotNull(scoreManagerTest.getWinner());
    }

    /**
     * Tests that the ScoreManager class is able to determine the winner by updating the score
     * during the frenzy Turn
     */
    @Test
    public void testFinalScore(){
        modelTest.getTurnManager().setFrenzy();
        scoreManagerTest.finalScore();
        assertNotNull(scoreManagerTest.getWinner());
    }

    /**
     * Test the getWinner method
     */
    @Test
    public void testGetWinner(){
        //multiple winners test
        player1.getScore().addScore(100);
        player1.setScoreFromKillShotTrack(10);
        player2.getScore().addScore(100);
        player2.setScoreFromKillShotTrack(10);
        player3.getScore().addScore(100);
        player3.setScoreFromKillShotTrack(10);
        player4.getScore().addScore(100);
        player4.setScoreFromKillShotTrack(10);

        assertNotNull(scoreManagerTest.getWinner());

    }

}
