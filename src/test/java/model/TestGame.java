package model;

import model.player.Player;
import model.player.PlayerColor;
import network.server.Server;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

public class TestGame {

    private Game gameTest;
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();
    private Server serverTest;

    @Before
    public void initGame(){
        player1 = new Player("player1", PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        try {
            serverTest = new Server();
        } catch (IOException e){
            e.printStackTrace();
        }
        gameTest = new Game(1, modelTest, serverTest);
    }

    @Test
    public void testMapVote(){
        int vote = gameTest.getMapVote();
        assertTrue((vote > 0) && (vote < 5));

        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(1);
        vote = gameTest.getMapVote();
        assertEquals(1, vote);

        modelTest.getMapVotes().clear();

        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(2);
        modelTest.getMapVotes().add(2);
        modelTest.getMapVotes().add(3);
        modelTest.getMapVotes().add(3);
        vote = gameTest.getMapVote();
        assertNotEquals(1, vote);
        assertTrue((vote > 1) && (vote < 4));

        modelTest.getMapVotes().clear();

        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(2);
        modelTest.getMapVotes().add(3);
        modelTest.getMapVotes().add(3);
        modelTest.getMapVotes().add(4);
        vote = gameTest.getMapVote();
        assertEquals(3, vote);

        modelTest.getMapVotes().clear();

        modelTest.getMapVotes().add(1);
        modelTest.getMapVotes().add(2);
        modelTest.getMapVotes().add(3);
        vote = gameTest.getMapVote();
        assertNotEquals(4, vote);
        assertTrue((vote > 0) && (vote < 4));
    }
}
