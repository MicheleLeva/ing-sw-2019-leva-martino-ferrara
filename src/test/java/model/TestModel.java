package model;

import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import model.turn.TurnManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static model.Model.runnableSquare;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Map;

/**
 * tests if the returned squares are actually those at distance 2 in all cardinal directions
 */
public class TestModel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model model ;
    private model.map.Map map ;
    private Square[][] squares;

    @Before
    /**
     * initializes the variables needed for the tests
     */
    public void setUp() throws Exception {
        map = new model.map.Map(1);
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

    @Test
    /**
     * tests if the returned players are those tha are visible from the current player
     */
    public void testVisiblePlayers(){
        ArrayList<Player> visiblePlayers;
        visiblePlayers = model.getVisiblePlayers(player1);
        assertTrue(visiblePlayers.contains(player2));
        assertTrue(visiblePlayers.contains(player3));


    }

    @Test
    /**
     * tests if the returned players are those that are not in the same room of the current player
     */
    public void testPlayersNotInYourRoom(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[0][0]);

        visiblePlayers = model.getPlayersNotInYourRoom(player1);
        assertTrue(visiblePlayers.contains(player3));
        assertEquals(visiblePlayers.size(), 1);
    }

    @Test
    /**
     * tests if the returned players are thos at distance inferior to the given one
     */
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
    /**
     * tests if the returned players are those at a distance superior to the given one
     */
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
    /**
     * tests if the returned players are those in the same square of the current player
     */
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
    /**
     * tests if the returned squares are actually those at distance 1 in all cardinal directions
     */
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
    /**
     * tests if the returned squares are actually those at distance 2 in all cardinal directions
     */
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
    /**
     * tests if the returned players are actually those in all 4 cardinal directions
     */
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
    /**
     * tests the returned squares are actually those i the selected cardinal direction
     */
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

    @Test @Ignore
    /**
     * tests if the returned visible squares from a player are correct
     */
    public void testGetVisibleSquares(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);

        visibleSquares = model.getVisibleSquares(player1);
        assertTrue(visibleSquares.contains(squares[0][0]));
        assertTrue(visibleSquares.contains(squares[0][1]));
        assertTrue(visibleSquares.contains(squares[0][2]));
        assertTrue(visibleSquares.contains(squares[1][0]));
        assertTrue(visibleSquares.contains(squares[1][1]));
        assertTrue(visibleSquares.contains(squares[1][2]));
        assertFalse(visibleSquares.contains(squares[2][2]));

    }

    @Test
    /**
     * tests if the returned squares at distance 1 in all 4 cardin directions are correct
     */
    public void testGetSquaresInCardinal1(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);

        visibleSquares = model.getSquaresInCardinal1(player1);
        assertTrue(visibleSquares.contains(squares[0][0]));
        assertTrue(visibleSquares.contains(squares[1][0]));
        assertTrue(visibleSquares.contains(squares[1][1]));
        assertFalse(visibleSquares.contains(squares[2][2]));
    }

    @Test
    /**
     * tests if the method returns players at the selected distance
     */
    public void testGetPlayersAtDistance(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersAtDistance(1,player1);
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
    }

    @Test
    /**
     * tests if the method returns players at the selected distance from a selected square
     */
    public void testGetPlayersAtDistanceFromSquare(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersAtDistance(1,player1,player1.getPosition());
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
    }

    @Test
    /**
     * tests if the method returns players in the selected cardinal direction
     */
    public void testGetPlayersInSeletedcardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[0][0]);

        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'n');
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'e');
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[2][1]);
        player3.setPosition(squares[1][0]);
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'s');
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'w');
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'x');
    }

    @Test
    /**
     * tests if the returned square for the power glove weapon is correct
     */
    public void testGetNextPowerGloveSquare(){
        Square square;
        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][1]);

        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == squares[1][3]);

        player1.setPosition(squares[1][2]);
        player2.setPosition(squares[1][1]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == squares[1][0]);

        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == null);

        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[2][1]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == null);

    }

    @Test
    /**
     * tests if the squares selected by the BFS method runnable square are correct
     */
    public void testRunnableSquare(){

        ArrayList<Square> test = new ArrayList<>();

        Square firstSquare = map.getSquareFromCoordinates(0, 0);

        test.add(firstSquare);
        assertEquals(runnableSquare(0, firstSquare), test);

        test.add(map.getSquareFromCoordinates(0,1));
        test.add(map.getSquareFromCoordinates(1,0));
        assertTrue(test.containsAll(runnableSquare(1,firstSquare)));
        assertEquals(test.size(), runnableSquare(1,firstSquare).size());

        test.add(map.getSquareFromCoordinates(0, 2));
        test.add(map.getSquareFromCoordinates(1, 1));
        assertTrue(test.containsAll(runnableSquare(2, firstSquare)));
        assertEquals(test.size(), runnableSquare(2, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1, 2));
        test.add(map.getSquareFromCoordinates(2, 1));
        assertTrue(test.containsAll(runnableSquare(3, firstSquare)));
        assertEquals(test.size(), runnableSquare(3, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1,3));
        test.add(map.getSquareFromCoordinates(2,2));
        assertTrue(test.containsAll(runnableSquare(4, firstSquare)));
        assertEquals(test.size(), runnableSquare(4, firstSquare).size());
    }

}