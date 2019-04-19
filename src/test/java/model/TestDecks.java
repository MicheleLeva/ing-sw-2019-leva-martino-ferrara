package model;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestDecks {
    private Decks decksTest;

    @Before
    public void initDecks(){
        decksTest = new Decks();
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
