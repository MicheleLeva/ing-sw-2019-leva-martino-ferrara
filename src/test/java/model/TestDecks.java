package model;
import static org.junit.Assert.*;

import model.cards.AmmoCard;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;
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

    /**
     * Initializes a deck to use for tests
     */
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

    /**
     * Tests that the method returns a weapon from deck
     */
    @Test
    public void testDrawWeapon() {
        Weapon weapon = decksTest.drawWeapon();
        assertNotNull(weapon);
        decksTest.getWeaponsDeck().clear();
        assertNull(decksTest.drawWeapon());
    }

    /**
     * Tests that the method returns a powerUp from deck
     */
    @Test
    public void testDrawPowerUp(){
        PowerUp powerUp = decksTest.drawPowerUp();
        assertNotNull(powerUp);
        decksTest.getPowerUpDeck().clear();
        assertNotNull(decksTest.drawWeapon());
    }

    /**
     * Tests that the method returns an AmmoCard from deck
     */
    @Test
    public void testDrawAmmoCard(){
        AmmoCard ammoCard = decksTest.drawAmmoCard();
        assertNotNull(ammoCard);
        decksTest.getDiscardedAmmoCardDeck().add(ammoCard);
        decksTest.getAmmoCardDeck().clear();
        assertNotNull(decksTest.drawAmmoCard());
    }

    /**
     * Tests the weapons deck getter
     */
    @Test
    public void testGetWeaponsDeck() {
        assertNotNull(decksTest.getWeaponsDeck());
    }

    /**
     * Tests the powerUp deck getter
     */
    @Test
    public void testGetPowerUpDeck() {
        assertNotNull(decksTest.getPowerUpDeck());
    }

    /**
     * Tests the AmmoCard deck getter
     */
    @Test
    public void testGetAmmoCardDeck() {
        assertNotNull(decksTest.getAmmoCardDeck());
    }

    /**
     * Tests the Discarded powerUp deck getter
     */
    @Test
    public void testGetDiscardedPowerUpDeck() {
        assertNotNull(decksTest.getDiscardedPowerUpDeck());
    }

    /**
     * Tests the Discarded ammo deck getter
     */
    @Test
    public void testGetDiscardedAmmoCardDeck() {
        assertNotNull(decksTest.getDiscardedAmmoCardDeck());
    }
}
