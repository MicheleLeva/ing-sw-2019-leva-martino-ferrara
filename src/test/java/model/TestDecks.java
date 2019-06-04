package model;
import static org.junit.Assert.*;

import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestDecks {
    private Decks decksTest;
    private Model modelTest;
    private Player player1 ;
    private Player player2;
    private Player player3;
    private ArrayList<Player> players = new ArrayList<>();

    @Before
    public void initDecks(){
        player1 = new Player("player1", PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        decksTest = new Decks(modelTest);
    }


    @Test
    public void testGetters(){
        assertNotNull(decksTest.getWeaponsDeck());
        assertNotNull(decksTest.getPowerUpDeck());
        assertNotNull(decksTest.getAmmoCardDeck());
        assertNotNull(decksTest.getDiscardedPowerUpDeck());
        assertNotNull(decksTest.getDiscardedAmmoCardDeck());


    }
    //Decks not yet ready
    /*@Test
    public void testDraws(){
        assertNotNull(decksTest.drawAmmoCard());
        assertNotNull(decksTest.drawPowerUp());

    }
    */
}
