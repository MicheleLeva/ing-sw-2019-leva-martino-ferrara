package controller;

import model.Model;
import model.cards.AmmoColor;
import model.cards.powerups.TagbackGrenade;
import model.cards.powerups.TargetingScope;
import model.cards.powerups.Teleporter;
import model.exchanges.events.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import view.View;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPowerUpController {

    private Model model;
    private Player player1 = new Player("player1", PlayerColor.BLUE);
    private Player player2 = new Player("player2", PlayerColor.GREEN);
    private Player player3 = new Player("player3", PlayerColor.PURPLE);
    private ArrayList<Player> players = new ArrayList<>();
    private PowerUpController powerUpController;
    private View view;

    @Before
    public void initPowerUpController(){
        view = new View(PlayerColor.BLUE);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        model = new Model(players,8);
        powerUpController = new PowerUpController(model);
        model.setGameBoard(1);
    }

    /**
     * Tests the correct execution for the choosePowerUpEvent update
     */
    @Test
    public void testChoosePowerUpEvent() {
        ChoosePowerUpEvent event = new ChoosePowerUpEvent(view,0);
        player1.setAfk(true);
        powerUpController.update(event);
        player1.setAfk(false);
        powerUpController.update(event);
        event = new ChoosePowerUpEvent(view,1);
        powerUpController.update(event);
        players.get(0).getResources().getPowerUp().add(new TargetingScope(model, AmmoColor.BLUE));
        powerUpController.update(event);
        event = new ChoosePowerUpEvent(view,1);
        powerUpController.update(event);
        event = new ChoosePowerUpEvent(view,4);
        powerUpController.update(event);
    }

    /**
     * Tests the correct execution for the chooseTeleporterSquare update
     */
    @Test
    public void testChooseTeleporterSquare() {
        ChooseTeleporterSquareEvent event = new ChooseTeleporterSquareEvent(view,15);
        player1.setAfk(true);
        powerUpController.update(event);
        player1.setAfk(false);
        powerUpController.update(event);
        assertNull(player1.getPosition());
        event = new ChooseTeleporterSquareEvent(view,1);
        powerUpController.update(event);
        assertSame(model.getGameBoard().getMap().getMap()[0][1],player1.getPosition());
    }

    /**
     * Tests the correct execution for the ChooseNewtonOpponent update
     */
    @Test
    public void testChooseNewtonOpponentEvent() {
        player2.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().getOpponent().add(player2);
        ChooseNewtonOpponentEvent event = new ChooseNewtonOpponentEvent(view,0);
        player1.setAfk(true);
        powerUpController.update(event);
        assertTrue(player1.isAfk());

        player1.setAfk(false);
        powerUpController.update(event);
        assertNull(model.getCurrent().getSelectedNewton());

        event = new ChooseNewtonOpponentEvent(view,1);
        powerUpController.update(event);
    }

    /**
     * Tests the correct execution for the ChooseNewtonSquare update
     */
    @Test
    public void testChooseNewtonSquareEvent() {
        player2.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().getOpponent().add(player2);
        ChooseNewtonSquareEvent event = new ChooseNewtonSquareEvent(view,-1);
        player1.setAfk(true);
        powerUpController.update(event);
        assertTrue(player1.isAfk());

        player1.setAfk(false);
        powerUpController.update(event);
        assertSame(player2.getPosition(), model.getGameBoard().getMap().getMap()[1][0]);

        event = new ChooseNewtonSquareEvent(view,1);
        player2.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().getOpponent().add(player2);
        powerUpController.update(event);
        assertSame(model.getGameBoard().getMap().getMap()[1][0],player2.getPosition());


        ArrayList<Square> squares = new ArrayList<>();
        squares.add(model.getGameBoard().getMap().getMap()[0][1]);
        model.getCurrent().setSquare(squares);
        assertSame(model.getGameBoard().getMap().getMap()[1][0],player2.getPosition());
    }

    /**
     * Tests the correct execution for the tTargetingScope update
     */
    @Test
    public void testTargetingScopeEvent () {
        TargetingScopeEvent event = new TargetingScopeEvent(view,'X');
        player1.setAfk(true);
        powerUpController.update(event);
        assertTrue(player1.isAfk());

        player1.setAfk(false);
        TargetingScope targetingScope = new TargetingScope(model,AmmoColor.BLUE);
        model.getCurrent().setLastTargetingScope(targetingScope);
        powerUpController.update(event);
        assertSame(model.getCurrent().getLastTargetingScope(),targetingScope);

        event = new TargetingScopeEvent(view,'N');
        powerUpController.update(event);
        assertNull(model.getCurrent().getLastTargetingScope());

        event = new TargetingScopeEvent(view,'Y');
        powerUpController.update(event);
        assertNull(model.getCurrent().getLastTargetingScope());

    }

    /**
     * Tests the correct execution for the TargetingScopeSelection update
     */
    @Test
    public void testTargetingScopeSelectionEvent() {
        ArrayList<Player> selected = new ArrayList<>();
        Teleporter teleporter = new Teleporter(model,AmmoColor.BLUE);
        player1.getResources().getPowerUp().add(teleporter);
        model.getCurrent().getAllDamagedPlayer().add(player2);
        TargetingScopeSelectionEvent event = new TargetingScopeSelectionEvent(view,0);
        player1.setAfk(true);
        powerUpController.update(event);
        player1.setAfk(false);
        powerUpController.update(event);
        event = new TargetingScopeSelectionEvent(view,1);
        selected.add(player2);
        model.getCurrent().setSelectedBaseTargets(selected);
        powerUpController.update(event);
        assertFalse(model.getCurrent().getAllDamagedPlayer().contains(player2));
    }

    /**
     * Tests the correct execution for the TagbackGrenade update
     */
    @Test
    public void testTagbackGrenadeEvent() {
        TagbackGrenadeEvent event = new TagbackGrenadeEvent(view,0);
        player1.setAfk(true);
        powerUpController.update(event);
        player1.setAfk(false);
        powerUpController.update(event);
        model.getTurnCurrent().getGrenadePeopleArray().add(player1);
        if(model.getTurnCurrent().isReceivedInput() || model.getTurnCurrent().getDeadPlayers().isEmpty())
            powerUpController.update(event);
        model.getTurnCurrent().getGrenadePeopleArray().add(player1);
        event = new TagbackGrenadeEvent(view,1);
        player1.getResources().getPowerUp().add(new TagbackGrenade(model,AmmoColor.BLUE));
        powerUpController.update(event);
        assertFalse(model.getTurnCurrent().getGrenadePeopleArray().contains(player1));

        model.getTurnCurrent().getGrenadePeopleArray().add(player1);
        event = new TagbackGrenadeEvent(view,1);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        powerUpController.update(event);
        assertTrue(model.getTurnCurrent().getGrenadePeopleArray().contains(player1));


    }

    /**
     * Tests the correct execution for the DiscardPowerUp update
     */
    @Test
    public void testDiscardPowerUpEvent() {
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        DiscardPowerUpEvent event = new DiscardPowerUpEvent(view,0);
        player1.setAfk(true);
        powerUpController.update(event);
        assertTrue(player1.isAfk());

        player1.setAfk(false);
        powerUpController.update(event);
        assertTrue(player1.getResources().getPowerUp().isEmpty());

        event = new DiscardPowerUpEvent(view,1);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        player1.setPosition(null);
        powerUpController.update(event);
        assertEquals(0, player1.getResources().getPowerUp().size());
    }

    /**
     * Tests that the cube of the correct color is removed after the Targeting Scope payment
     */
    @Test
    public void testScopePayment(){
        ScopePaymentEvent event = new ScopePaymentEvent(view,'?');
        player1.setAfk(true);
        powerUpController.update(event);
        player1.setAfk(false);
        powerUpController.update(event);
        event = new ScopePaymentEvent(view,'r');
        powerUpController.update(event);
        assertEquals(0, player1.getResources().getAvailableAmmo().getRed());

        event = new ScopePaymentEvent(view,'b');
        powerUpController.update(event);
        assertEquals(0, player1.getResources().getAvailableAmmo().getBlue());

        event = new ScopePaymentEvent(view,'y');
        powerUpController.update(event);
        assertEquals(0, player1.getResources().getAvailableAmmo().getYellow());



    }
}