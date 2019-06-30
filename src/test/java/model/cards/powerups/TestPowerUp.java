package model.cards.powerups;

import model.game.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.adrenaline_exceptions.TargetingScopeException;
import model.cards.AmmoColor;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPowerUp {
    private Model model;
    private ArrayList<Player> players;
    @Before
    public void initTestPowerUp(){
        players = new ArrayList<>();
        Player player1 = new Player("player1", PlayerColor.BLUE);
        Player player2 = new Player("player2",PlayerColor.YELLOW);
        Player player3 = new Player("player3",PlayerColor.GREEN);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        model = new Model(players,8);
        model.setGameBoard(1);

    }

    /**
     * Tests the correct use of the Targeting Scope PowerUp
     * @throws TagbackGrenadeException exception that prevents a client from using the TagBackGrenade PowerUp
     * in the wrong moment
     */
    @Test(expected = TagbackGrenadeException.class)

    public void testTagBackGrenade() throws TagbackGrenadeException {
        TagbackGrenade tagbackGrenade = new TagbackGrenade(model, AmmoColor.BLUE);
        tagbackGrenade.usePowerUp(players.get(0).getPlayerColor());
    }

    /**
     * Tests the correct use of the Newton PowerUp
     */
    @Test
    public void testNewton() {
        ArrayList<PowerUp> powerups = new ArrayList<>();
        Newton newton = new Newton(model, AmmoColor.BLUE);
        powerups.add(newton);
        players.get(0).getResources().addPowerUp(powerups);
        newton.usePowerUp(players.get(0).getPlayerColor());
        assertTrue(players.get(0).getResources().getPowerUp().contains(newton));
    }

    /**
     * Tests the correct use of the Teleporter PowerUp
     */
    @Test
    public void testTeleporter() {
        ArrayList<PowerUp> powerups = new ArrayList<>();
        Teleporter teleporter = new Teleporter(model, AmmoColor.BLUE);
        powerups.add(teleporter);
        players.get(0).getResources().addPowerUp(powerups);
        teleporter.usePowerUp(players.get(0).getPlayerColor());
        assertFalse(players.get(0).getResources().getPowerUp().contains(teleporter));
    }

    /**
     * Tests the correct use of the Targeting Scope PowerUp
     * @throws TargetingScopeException exception that prevents a client from using the Targeting Scope in
     * the wrong moment
     */
    @Test(expected = TargetingScopeException.class)

    public void testTargetingScope() throws TargetingScopeException {
        ArrayList<PowerUp> powerups = new ArrayList<>();

        TargetingScope targetingScope = new TargetingScope(model, AmmoColor.BLUE);
        assertSame(model, targetingScope.getModel());

        powerups.add(targetingScope);
        players.get(0).getResources().addPowerUp(powerups);
        targetingScope.usePowerUp(players.get(0).getPlayerColor());
        assertTrue(players.get(0).getResources().getPowerUp().contains(targetingScope));
    }


}