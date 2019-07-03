package controller;

import model.game.Ammo;
import model.game.Model;
import model.cards.AmmoColor;
import model.cards.powerups.PowerUp;
import model.cards.powerups.Teleporter;
import model.cards.weapons.*;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the behaviour of the methods in the class Checks
 * @author Stefano Martino, Marco Maria Ferrara
 */
public class TestChecks {


    /**
     * Tests if player has max number of powerups
     */
    @Test
    public void hasMaxPowerUp() {
        Player player = new Player("player",PlayerColor.BLUE);
        assertFalse(Checks.hasMaxPowerUp(player));
    }

    /**
     * Tests the number of ammos actually drawn
     */
    @Test
    public void testDrawnAmmo() {
        Ammo grabbedAmmo = new Ammo(0,2,3);
        Ammo playerAmmo = new Ammo(1,1,1);
        Ammo drawnAmmo = Checks.drawnAmmo(grabbedAmmo,playerAmmo);
        assertEquals(0,drawnAmmo.getRed());
        assertEquals(2,drawnAmmo.getBlue());
        assertEquals(2,drawnAmmo.getYellow());


    }

    /**
     * Tests if the player can use the reload action
     */
    @Test
    public void testCanReload() {
        Ammo cost = new Ammo(1,1,1);
        Heatseeker heatseeker = new Heatseeker("heatseeker",cost,cost,1,1,1,null);
        ArrayList<Weapon> weapons = new ArrayList<>();
        weapons.add(heatseeker);
        boolean bool = Checks.canReload(weapons,new Ammo(2,2,2));
        assertTrue(bool);
        bool = Checks.canReload(weapons,new Ammo(0,0,0));
        assertFalse(bool);
    }

    /**
     * Tests if the player can use a selected Fire Mode
     */
    @Test
    public void testCanUseFireMode() {
        Ammo cost = new Ammo(1,1,1);
        Player player = new Player("player2", PlayerColor.BLUE);
        Weapon electroscythe = new Electroscythe("electro",cost,cost,cost,1,1,
                1,1,1,1,null);
        Weapon machinegun = new Machinegun("machin",cost,cost,cost,cost,1,1,
                1,1,1,1,1,1,1,null);
        assertTrue(Checks.canUseFireMode(player,electroscythe,"return"));
        assertTrue(Checks.canUseFireMode(player,electroscythe,"base"));
        assertTrue(Checks.canUseFireMode(player,electroscythe,"end"));
        assertTrue(Checks.canUseFireMode(player,electroscythe,"alternative"));
        assertTrue(Checks.canUseFireMode(player,machinegun,"optional1"));
        assertTrue(Checks.canUseFireMode(player,machinegun,"optional2"));
        assertFalse(Checks.canUseFireMode(player,machinegun,"abcd"));

    }

    /**
     * Tests the amount of damage given from one player to another
     */
    @Test
    public void givenDamage() {
        int playerDamage = 8;
        int damage = 6;
        int result = Checks.givenDamage(playerDamage,damage);
        assertEquals(4,result);
        damage = 2;
        result = Checks.givenDamage(playerDamage,damage);
        assertEquals(2,result);
    }

    /**
     * Tests the number of marks given from one player to another
     */
    @Test
    public void givenMark() {
        int playerMarks = 2;
        int marks = 3;
        int result = Checks.givenMark(playerMarks,marks);
        assertEquals(1,result);
        marks = 0;
        result = Checks.givenMark(playerMarks,marks);
        assertEquals(0,result);
    }

    /**
     * Tests, based on the damage received, what are the next available actions
     */
    @Test
    public void testVerifyNewAction() {
        Player player = new Player("player1",PlayerColor.BLUE);
        int result = Checks.verifyNewAction(player);
        assertEquals(1,result);
        player.getPlayerBoard().getDamageCounter().addDamage(PlayerColor.YELLOW,3);
        result = Checks.verifyNewAction(player);
        assertEquals(2,result);
        player.getPlayerBoard().getDamageCounter().addDamage(PlayerColor.YELLOW,5);
        result = Checks.verifyNewAction(player);
        assertEquals(3,result);

    }

    /**
     * Tests getter for MAXDAMAGE
     */
    @Test
    public void testGetMaxDamage() {
        assertEquals(12,Checks.getMaxDamage());
    }

    /**
     * Tests getter for KILLSHOT
     */
    @Test
    public void testGetKillshot() {
        assertEquals(11,Checks.getKillshot());
    }

    /**
     * Tests getter for DOUBLEKILLSHOT
     */
    @Test
    public void testGetDoubleKillShot() {
        assertEquals(2,Checks.getDoubleKillShot());

    }

    /**
     * Tests if the selected payment option is valid
     */
    @Test
    public void testValidPayment() {
        Player player1 = new Player("player1",PlayerColor.BLUE);
        Player player2 = new Player("player2",PlayerColor.GREEN);
        Player player3 = new Player("player3",PlayerColor.PURPLE);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);


        Ammo cost = new Ammo(1,1,1);
        PowerUp powerUp1 = new Teleporter(null, AmmoColor.BLUE);
        PowerUp powerUp2 = new Teleporter(null, AmmoColor.RED);
        PowerUp powerUp3 = new Teleporter(null, AmmoColor.YELLOW);

        Weapon electroscythe = new Electroscythe("electro",cost,cost,cost,1,1,
                1,1,1,1,null);
        Weapon machinegun = new Machinegun("machine",cost,cost,cost,cost,1,1,
                1,1,1,1,1,1,1,null);
        ArrayList<Integer> choices= new ArrayList<>();
        choices.add(1);
        choices.add(2);
        choices.add(3);
        Model model = new Model(players,8);
        model.getCurrent().addAvailablePaymentPowerUps(powerUp1);
        model.getCurrent().addAvailablePaymentPowerUps(powerUp2);
        model.getCurrent().addAvailablePaymentPowerUps(powerUp3);
        model.getCurrent().setSelectedPickUpWeapon(electroscythe);
        assertTrue(Checks.validPayment(player1,choices,"pickup",model));
        assertTrue(Checks.validPayment(player1,choices,"reload",model));
        assertTrue(Checks.validPayment(player1,choices,"alternative",model));
        model.getCurrent().setSelectedPickUpWeapon(machinegun);
        assertTrue(Checks.validPayment(player1,choices,"optional1",model));
        assertTrue(Checks.validPayment(player1,choices,"optional2",model));
        assertFalse(Checks.validPayment(player1,choices,"abcd",model));

    }
}