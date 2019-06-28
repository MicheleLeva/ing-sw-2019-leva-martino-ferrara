package controller;

import model.Ammo;
import model.Model;
import model.cards.AmmoColor;
import model.cards.powerups.TagbackGrenade;
import model.cards.powerups.TargetingScope;
import model.cards.powerups.Teleporter;
import model.cards.weapons.*;
import model.exchanges.events.*;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import view.View;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestWeaponController {

    private Ammo ammo = new Ammo(0,0,0);
    private Model model;
    private Player player1 = new Player("player1", PlayerColor.BLUE);
    private Player player2 = new Player("player2", PlayerColor.GREEN);
    private Player player3 = new Player("player3", PlayerColor.PURPLE);
    private ArrayList<Player> players = new ArrayList<>();
    private WeaponController weaponController;
    private View view;
    private Electroscythe electroscythe;
    private Cyberblade cyberblade;

    @Before
    public void initWeaponController(){
        view = new View(PlayerColor.BLUE);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        model = new Model(players,8);
        weaponController = new WeaponController(model);
        model.setGameBoard(1);
        electroscythe = new Electroscythe("ELECTRO",ammo,ammo,ammo,1,
                1,1,1,1,1,model);
        electroscythe.setWeaponTree(new WeaponTree("src/resources/Electroscythe.json"));
        cyberblade = new Cyberblade("CYBER",ammo,ammo,ammo,ammo,1,
                1,1,1,1,1,1,1,1,model);
        cyberblade.setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
    }

    /**
     * tests the correct execution for the WeaponSelection update
     */
    @Test
    public void testWeaponSelectionEvent() {
        WeaponSelectionEvent event = new WeaponSelectionEvent(view,0);
        player1.setAfk(true);
        weaponController.update(event);
        player1.setAfk(false);
        weaponController.update(event);
        event = new WeaponSelectionEvent(view,1);
        electroscythe.reload();
        player1.getResources().addWeapon(electroscythe);
        weaponController.update(event);
        assertSame(model.getCurrent().getSelectedWeapon(),electroscythe);

    }

    /**
     * tests the correct execution for the WeaponReload update
     */
    @Test
    public void testWeaponReloadEvent() {
        WeaponReloadEvent event = new WeaponReloadEvent(view,0);
        player1.setAfk(true);
        weaponController.update(event);
        player1.setAfk(false);
        weaponController.update(event);
        event = new WeaponReloadEvent(view,1);
        electroscythe.reload();
        player1.getResources().addWeapon(electroscythe);
        weaponController.update(event);
        assertNull(model.getCurrent().getSelectedWeapon());
    }

    /**
     * tests the correct execution for the OptionalFireModes update
     */
    @Test
    public void testOptionalFireModesEvent() {
        model.getCurrent().setSelectedWeapon(electroscythe);
        OptionalFireModesEvent event = new OptionalFireModesEvent(view,0);
        player1.setAfk(true);
        weaponController.update(event);
        player1.setAfk(false);
        weaponController.update(event);
        event = new OptionalFireModesEvent(view,1);
        model.getCurrent().setSelectedWeapon(electroscythe);
        model.getCurrent().getAvailableFireModes().clear();
        model.getCurrent().getAvailableFireModes().add(new WeaponTreeNode<>(new FireMode("return","a")));
        weaponController.update(event);
        model.getCurrent().setSelectedWeapon(electroscythe);
        model.getCurrent().getAvailableFireModes().clear();
        model.getCurrent().getAvailableFireModes().add(new WeaponTreeNode<>(new FireMode("base","b")));
        weaponController.update(event);
        assertSame(electroscythe,model.getCurrent().getSelectedWeapon());
        model.getCurrent().setSelectedWeapon(electroscythe);
        player1.getResources().getPowerUp().add(new TargetingScope(model,AmmoColor.BLUE));
        model.getCurrent().getAvailableFireModes().clear();
        model.getCurrent().getAvailableFireModes().add(new WeaponTreeNode<>(new FireMode("end","c")));
        weaponController.update(event);
        assertNull(model.getCurrent().getSelectedWeapon());
        model.getCurrent().setSelectedWeapon(electroscythe);
        model.getCurrent().getAvailableFireModes().clear();
        model.getCurrent().getAvailableFireModes().add(new WeaponTreeNode<>(new FireMode("alternative","d")));
        weaponController.update(event);
        assertSame(electroscythe,model.getCurrent().getSelectedWeapon());
    }

    /**
     * tests the correct execution for the TargetsSelection update
     */
    @Test
    public void testTargetsSelectionEvent() {

        model.getCurrent().setSelectedWeapon(electroscythe);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,new ArrayList<>());
        weaponController.update(event);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("base","a")));
        weaponController.update(event);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("alternative","a")));
        weaponController.update(event);
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional1","a")));
        weaponController.update(event);
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional2","a")));
        weaponController.update(event);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        event = new TargetsSelectionEvent(view,integers);
        model.getCurrent().getAvailableBaseTargets().add(player2);
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("base","a")));
        weaponController.update(event);
        model.getCurrent().getAvailableAlternativeTargets().add(player2);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("alternative","a")));
        weaponController.update(event);
        model.getCurrent().getAvailableOptionalTargets1().add(player2);
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional1","a")));
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        player2.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        weaponController.update(event);
        model.getCurrent().getAvailableOptionalTargets2().add(player2);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional2","a")));
        model.getCurrent().getSelectedBaseTargets().add(player2);
        weaponController.update(event);
        assertNull(model.getCurrent().getSelectedWeapon());
        assertEquals(0,model.getCurrent().getBaseCounter());
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        assertEquals(0,model.getCurrent().getOptionalCounter1());
        assertEquals(0,model.getCurrent().getOptionalCounter2());


    }

    /**
     * tests the correct execution for the ChooseWeaponSquare update
     */
    @Test
    public void testChooseWeaponSquareEvent() {
        ChooseWeaponSquareEvent event = new ChooseWeaponSquareEvent(view,12);
        model.getCurrent().getAvailableWeaponSquares().add(model.getGameBoard().getMap().getMap()[0][0]);
        weaponController.update(event);
        event = new ChooseWeaponSquareEvent(view,0);
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("base","a")));
        weaponController.update(event);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("alternative","a")));
        weaponController.update(event);
        model.getCurrent().setSelectedWeapon(cyberblade);
        model.getCurrent().getAvailableWeaponSquares().clear();
        model.getCurrent().getAvailableWeaponSquares().add(model.getGameBoard().getMap().getMap()[0][0]);
        event = new ChooseWeaponSquareEvent(view,0);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional1","a")));
        player1.setPosition(model.getGameBoard().getMap().getMap()[0][0]);
        player2.setPosition(model.getGameBoard().getMap().getMap()[0][0]);
        weaponController.update(event);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional2","a")));
        model.getCurrent().getSelectedBaseTargets().add(player2);
        weaponController.update(event);
    }

    /**
     * tests the correct execution for the WeaponPayment update
     */
    @Test
    public void testWeaponPaymentEvent() {
        ArrayList<Player> selected = new ArrayList<>();
        player1.setPosition(model.getGameBoard().getMap().getMap()[0][0]);
        ArrayList<Integer> choices = new ArrayList<>();
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model, AmmoColor.BLUE));
        WeaponPaymentEvent event = new WeaponPaymentEvent(view,choices);
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("alternative","a")));
        weaponController.update(event);
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional1","a")));
        weaponController.update(event);
        cyberblade.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("optional2","a")));
        selected.add(player2);
        model.getCurrent().setSelectedBaseTargets(selected);
        weaponController.update(event);
        choices.clear();
        choices.add(15);
        event = new WeaponPaymentEvent(view,choices);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.getWeaponTree().setLastAction(new WeaponTreeNode<>(new FireMode("alternative","a")));
        weaponController.update(event);
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model, AmmoColor.BLUE));
        choices.clear();
        choices.add(1);
        event = new WeaponPaymentEvent(view,choices);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        weaponController.update(event);
        assertEquals(1,player1.getResources().getAvailableAmmo().getRed());
        assertEquals(1,player1.getResources().getAvailableAmmo().getBlue());
        assertEquals(1,player1.getResources().getAvailableAmmo().getYellow());

    }


    /**
     * tests the correct execution for the ReloadPayment update
     */
    @Test
    public void testReloadPaymentEvent() {
        model.getCurrent().setSelectedWeapon(electroscythe);
        ArrayList<Integer> choices = new ArrayList<>();
        choices.add(7);
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model,AmmoColor.BLUE));
        ReloadPaymentEvent event = new ReloadPaymentEvent(view,choices);
        weaponController.update(event);
        choices.clear();
        choices.add(1);
        event = new ReloadPaymentEvent(view,choices);
        model.getCurrent().setSelectedWeapon(electroscythe);
        weaponController.update(event);
        choices.clear();
        event = new ReloadPaymentEvent(view,choices);
        model.getCurrent().setSelectedWeapon(electroscythe);
        weaponController.update(event);
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);
        model.getCurrent().setSelectedWeapon(electroscythe);
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model, AmmoColor.BLUE));
        choices.clear();
        choices.add(1);
        event = new ReloadPaymentEvent(view,choices);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        weaponController.update(event);
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);

    }

    /**
     * tests the correct execution for the PickUpPayment update
     */
    @Test
    public void testPickUpPaymentEvent() {
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().setSelectedPickUpWeapon(electroscythe);
        ArrayList<Integer> choices = new ArrayList<>();
        choices.add(7);
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model,AmmoColor.BLUE));
        PickUpPaymentEvent event = new PickUpPaymentEvent(view,choices);
        weaponController.update(event);
        choices.clear();
        choices.add(1);
        event = new PickUpPaymentEvent(view,choices);
        model.getCurrent().setSelectedPickUpWeapon(electroscythe);
        weaponController.update(event);
        choices.clear();
        event = new PickUpPaymentEvent(view,choices);
        model.getCurrent().setSelectedPickUpWeapon(electroscythe);
        weaponController.update(event);
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);
        model.getCurrent().setSelectedPickUpWeapon(electroscythe);
        model.getCurrent().getAvailablePaymentPowerUps().add(new Teleporter(model, AmmoColor.BLUE));
        choices.clear();
        choices.add(1);
        event = new PickUpPaymentEvent(view,choices);
        player1.getResources().getPowerUp().add(new Teleporter(model,AmmoColor.BLUE));
        weaponController.update(event);
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),1);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);

    }

    /**
     * tests the correct execution for the ChoosePickUpWeapon update
     */
    @Test
    public void testChoosePickUpWeaponEvent() {
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        ChoosePickUpWeaponEvent event = new ChoosePickUpWeaponEvent(view,0);
        weaponController.update(event);
        event = new ChoosePickUpWeaponEvent(view,1);
        model.getCurrent().getPickUpableWeapon().add(electroscythe);
        weaponController.update(event);
    }

    /**
     * tests the correct execution for the WeaponSwap update
     */
    @Test
    public void testWeaponSwapEvent () {
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        player1.getResources().getAllWeapon().add(electroscythe);
        WeaponSwapEvent event = new WeaponSwapEvent(view,0);
        weaponController.update(event);
        event = new WeaponSwapEvent(view,1);
        weaponController.update(event);

    }

    /**
     * tests the correct execution for the checkBaseTargets method
     */
    @Test
    public void testCheckBaseTargets() {
        model.getCurrent().setSelectedWeapon(cyberblade);
        model.getCurrent().getAvailableBaseTargets().add(player2);
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(-1);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,selected);
        weaponController.checkBaseTargets(event,electroscythe,model.getCurrent(),player1);
        model.getCurrent().getAvailableBaseTargets().add(player2);
        selected.clear();
        selected.add(1);
        event = new TargetsSelectionEvent(view,selected);
        weaponController.checkBaseTargets(event,electroscythe,model.getCurrent(),player1);
        assertTrue(model.getCurrent().getSelectedBaseTargets().isEmpty());
    }
    /**
     * tests the correct execution for the checkAlternativeTargets method
     */
    @Test
    public void testCheckAlternativeTargets() {
        model.getCurrent().setSelectedWeapon(cyberblade);
        model.getCurrent().getAvailableAlternativeTargets().add(player2);
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(-1);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,selected);
        weaponController.checkAlternativeTargets(event,electroscythe,model.getCurrent(),player1);
        model.getCurrent().getAvailableAlternativeTargets().add(player2);
        selected.clear();
        selected.add(1);
        event = new TargetsSelectionEvent(view,selected);
        weaponController.checkAlternativeTargets(event,electroscythe,model.getCurrent(),player1);
        assertTrue(model.getCurrent().getSelectedBaseTargets().isEmpty());
    }

    /**
     * tests the correct execution for the checkOptionalTargets1 method
     */
    @Test
    public void testCheckOptionalTargets1() {
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().setSelectedWeapon(cyberblade);
        model.getCurrent().getAvailableOptionalTargets1().add(player2);
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(-1);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,selected);
        weaponController.checkOptionalTargets1(event,cyberblade,model.getCurrent(),player1);
        model.getCurrent().getAvailableOptionalTargets1().add(player2);
        selected.clear();
        selected.add(1);
        event = new TargetsSelectionEvent(view,selected);
        weaponController.checkOptionalTargets1(event,cyberblade,model.getCurrent(),player1);
        assertTrue(model.getCurrent().getSelectedBaseTargets().isEmpty());

    }

    /**
     * tests the correct execution for the checkOptionalTargets2 method
     */
    @Test
    public void testCheckOptionalTargets2() {
        player1.setPosition(model.getGameBoard().getMap().getMap()[1][0]);
        model.getCurrent().setSelectedWeapon(cyberblade);
        model.getCurrent().getAvailableOptionalTargets2().add(player2);
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(-1);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,selected);
        model.getCurrent().getAvailableOptionalTargets2().add(player2);
        weaponController.checkOptionalTargets2(event,cyberblade,model.getCurrent(),player1);
        model.getCurrent().getAvailableOptionalTargets2().add(player2);
        selected.clear();
        selected.add(1);
        event = new TargetsSelectionEvent(view,selected);
        model.getCurrent().getSelectedBaseTargets().add(player2);
        weaponController.checkOptionalTargets2(event,cyberblade,model.getCurrent(),player1);
        assertFalse(model.getCurrent().getSelectedBaseTargets().isEmpty());

    }

    /**
     * Tests if the method can correctly detect duplicates in the targets selection
     */

    @Test
    public void testHasDuplicates(){
        ArrayList<Integer> targets = new ArrayList<>();

        targets.add(1);
        TargetsSelectionEvent event = new TargetsSelectionEvent(view,targets);
        assertFalse(weaponController.hasDuplicates(event));

        targets.add(2);
        event = new TargetsSelectionEvent(view,targets);
        assertFalse(weaponController.hasDuplicates(event));

        targets.add(1);
        event = new TargetsSelectionEvent(view,targets);
        assertTrue(weaponController.hasDuplicates(event));
    }

    /**
     * Tests if the method can correctly detect duplicates in the payment selection
     */

    @Test
    public void testHasDuplicatePayment(){
        ArrayList<Integer> payment = new ArrayList<>();

        payment.add(1);
        assertFalse(weaponController.hasDuplicatePayment(payment));

        payment.add(2);
        assertFalse(weaponController.hasDuplicatePayment(payment));

        payment.add(1);
        assertTrue(weaponController.hasDuplicatePayment(payment));
    }
}