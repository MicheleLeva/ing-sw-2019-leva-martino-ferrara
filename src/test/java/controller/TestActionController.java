package controller;


import model.Model;
import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import view.View;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestActionController {

    private Player player1 ;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();
    private ActionController actionControllerTest;
    private View blueView;
    private View greenView;

    @Before
    public void initGame(){
        player1 = new Player("player1", PlayerColor.BLUE);
        Player player2 = new Player("player2",PlayerColor.GREEN);
        Player player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        actionControllerTest = new ActionController(modelTest);
        blueView = new View(player1.getPlayerColor());
        greenView = new View(player2.getPlayerColor());
    }

    @Test
    public void  testActionEvent(){
        ActionEvent actionEvent = new ActionEvent(blueView, 'Z');
        ActionEvent invalidEvent = new ActionEvent(blueView, 'k');
        ActionEvent reloadEvent = new ActionEvent(blueView, 'R');
        ActionEvent greenEvent = new ActionEvent(greenView, 'Z');

        modelTest.setPlayerAfk(player1);
        actionControllerTest.update(actionEvent);
        actionControllerTest.update(actionEvent);
        actionControllerTest.update(actionEvent);
        assertFalse(player1.getActionTree().isTurnEnded());

        modelTest.wakeUpPlayer(player1);
        actionControllerTest.update(actionEvent);
        actionControllerTest.update(actionEvent);
        actionControllerTest.update(actionEvent);
        assertTrue(player1.getActionTree().isTurnEnded());

        player1.getActionTree().resetAction();

        actionControllerTest.update(actionEvent);
        actionControllerTest.update(reloadEvent);
        assertFalse(player1.getActionTree().isTurnEnded());

        player1.getActionTree().resetAction();

        actionControllerTest.update(actionEvent);
        actionControllerTest.update(greenEvent);
        assertFalse(player1.getActionTree().isTurnEnded());

        player1.getActionTree().resetAction();

        actionControllerTest.update(actionEvent);
        actionControllerTest.update(invalidEvent);
        assertFalse(player1.getActionTree().isTurnEnded());


    }

    @Test
    public void  testQuitAfkEvent(){
        modelTest.setPlayerAfk(player1);
        QuitAfkEvent quitAfkEvent = new QuitAfkEvent(blueView, 'Z');
        actionControllerTest.update(quitAfkEvent);
        assertFalse(player1.isAfk());
    }

    @Test
    public void  testVoteMapEvent(){
        modelTest.setPlayerAfk(player1);
        VoteMapEvent voteMapEvent = new VoteMapEvent(blueView, '1');
        actionControllerTest.update(voteMapEvent);
        assertTrue(modelTest.getMapVotes().isEmpty());

        modelTest.wakeUpPlayer(player1);
        actionControllerTest.update(voteMapEvent);
        assertFalse(modelTest.getMapVotes().isEmpty());

        modelTest.getMapVotes().clear();

        VoteMapEvent invalidEvent = new VoteMapEvent(blueView, 5);
        actionControllerTest.update(invalidEvent);
        assertTrue(modelTest.getMapVotes().isEmpty());

        VoteMapEvent notEvent = new VoteMapEvent(blueView, 0);
        actionControllerTest.update(notEvent);
        assertTrue(modelTest.getMapVotes().isEmpty());
    }
}
