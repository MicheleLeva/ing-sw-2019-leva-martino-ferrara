package model;

import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import model.turn.TurnManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Map;

public class TestModel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model model ;
    private model.map.Map map ;
    private Square[][] squares;

    @Before
    public void setUp() throws Exception {
        map = new model.map.Map(3);
        squares = map.getMap();
        player1 = new Player("player1",PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[1][2]);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        model = new Model(players,8);
    }
   /* @Test
    public void testRunnableSquare(){
        ArrayList<Square> squares = Model.runnableSquare(3,player1.getPosition());
        for(Square square : squares)
            System.out.println(square.getID());
    }*/

    @Test
    public void testVisiblePlayers(){
        ArrayList<Player> visiblePlayers;
        visiblePlayers = model.getVisiblePlayers(player1);
        assertEquals(visiblePlayers.get(0),player2);
        assertEquals(visiblePlayers.get(1),player3);


    }

    @Test
    public void testPlayersNotInYourRoom(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[0][0]);

        visiblePlayers = model.getPlayersNotInYourRoom(player1);
        assertEquals(visiblePlayers.get(0),player3);
        assertEquals(visiblePlayers.size(), 1);
    }

    @Test
    public void testPlayersAtDistance(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][2]);

        visiblePlayers = model.getPlayersAtDistance(3,player1);
        assertTrue(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),2);
    }

    @Test
    public void testPlayersAtDistanceMore(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][3]);

        visiblePlayers = model.getPlayersAtDistanceMore(3,player1);
        System.out.println(visiblePlayers+"\n");
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    public void testPlayersInSameSquare(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][0]);
        player3.setPosition(squares[1][3]);

        visiblePlayers = model.getPlayersInSameSquare(player1);
        System.out.println(visiblePlayers+"\n");
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    public void testNonVisiblePlayers(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getNonVisiblePlayers(player1);
        System.out.println(visiblePlayers+"\n");
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    public void testSquaresInCardinal2(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][3]);

        visibleSquares = model.getSquaresInCardinal2(player1);
        //visibleSquares.stream().map(x->x.getID()).forEach(x->System.out.println(x));
        assertEquals(visibleSquares.size(),4);
    }

    @Test
    public void testPlayersInCardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersInCardinalDirection(player1);
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    public void testPlayersInSelectedardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'e');
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }


}