package model.exchanges;

import model.exchanges.events.*;
import model.exchanges.messages.ScopePaymentMessage;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import view.View;

import java.util.ArrayList;

/**
 * Tests that all the events used for the game are created correctly
 */
public class TestEvents {

    private View testView;
    private ArrayList<Integer> integers;

    @Before
    public void initEvents(){
        testView = new View(PlayerColor.BLUE);
        integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
    }

    /**
     * Tests the correct construction of all the events
     */
    @Test
    public void testEvents(){
        ActionEvent actionEvent = new ActionEvent(testView, 'S');
        assertEquals("ActionEvent,S",actionEvent.toString());

        ChooseNewtonOpponentEvent chooseNewtonOpponentEvent = new ChooseNewtonOpponentEvent(testView, 1);
        assertEquals("ChooseNewtonOpponentEvent,1",chooseNewtonOpponentEvent.toString());

        ChooseNewtonSquareEvent chooseNewtonSquareEvent = new ChooseNewtonSquareEvent(testView, 1);
        assertEquals("ChooseNewtonSquareEvent,1", chooseNewtonSquareEvent.toString());

        ChoosePickUpWeaponEvent choosePickUpWeaponEvent = new ChoosePickUpWeaponEvent(testView, 1);
        assertEquals("ChoosePickUpWeaponEvent,1", choosePickUpWeaponEvent.toString());

        ChoosePowerUpEvent choosePowerUpEvent = new ChoosePowerUpEvent(testView,1);
        assertEquals("ChoosePowerUpEvent,1",choosePowerUpEvent.toString());

        ChooseTeleporterSquareEvent chooseTeleporterSquareEvent = new ChooseTeleporterSquareEvent(testView,1);
        assertEquals("ChooseTeleporterSquareEvent,1",chooseTeleporterSquareEvent.toString());

        ChooseWeaponSquareEvent chooseWeaponSquareEvent = new ChooseWeaponSquareEvent(testView,1);
        assertEquals("ChooseWeaponSquareEvent,1",chooseWeaponSquareEvent.toString());

        DiscardPowerUpEvent discardPowerUpEvent = new DiscardPowerUpEvent(testView,1);
        assertEquals("DiscardPowerUpEvent,1", discardPowerUpEvent.toString());

        OptionalFireModesEvent optionalFireModesEvent = new OptionalFireModesEvent(testView,1);
        assertEquals("OptionalFireModesEvent,1", optionalFireModesEvent.toString());

        PickUpPaymentEvent pickUpPaymentEvent = new PickUpPaymentEvent(testView, integers);
        assertEquals("PickUpPaymentEvent,1,2",pickUpPaymentEvent.toString());

        QuitAfkEvent quitAfkEvent = new QuitAfkEvent(testView,'a');
        assertEquals("QuitAfkEvent,a",quitAfkEvent.toString());

        ReloadPaymentEvent reloadPaymentEvent = new ReloadPaymentEvent(testView, integers);
        assertEquals("ReloadPaymentEvent,1,2",reloadPaymentEvent.toString());

        TagbackGrenadeEvent tagbackGrenadeEvent = new TagbackGrenadeEvent(testView,1);
        assertEquals("TagbackGrenadeEvent,1",tagbackGrenadeEvent.toString());

        TargetingScopeEvent targetingScopeEvent = new TargetingScopeEvent(testView,'a');
        assertEquals("TargetingScopeEvent,a",targetingScopeEvent.toString());

        TargetingScopeSelectionEvent targetingScopeSelectionEvent = new TargetingScopeSelectionEvent(testView,1);
        assertEquals("TargetingScopeSelectionEvent,1",targetingScopeSelectionEvent.toString());

        TargetsSelectionEvent targetsSelectionEvent = new TargetsSelectionEvent(testView,integers);
        assertEquals("TargetsSelectionEvent,1,2",targetsSelectionEvent.toString());

        VoteMapEvent voteMapEvent = new VoteMapEvent(testView, 1);
        assertEquals("VoteMapEvent,1", voteMapEvent.toString());

        WeaponPaymentEvent weaponPaymentEvent = new WeaponPaymentEvent(testView, integers);
        assertEquals("WeaponPaymentEvent,1,2", weaponPaymentEvent.toString());

        WeaponReloadEvent weaponReloadEvent = new WeaponReloadEvent(testView, 1);
        assertEquals("WeaponReloadEvent,1", weaponReloadEvent.toString());

        WeaponSelectionEvent weaponSelectionEvent = new WeaponSelectionEvent(testView, 1);
        assertEquals("WeaponSelectionEvent,1", weaponSelectionEvent.toString());

        WeaponSwapEvent weaponSwapEvent = new WeaponSwapEvent(testView, 1);
        assertEquals("WeaponSwapEvent,1", weaponSwapEvent.toString());

        ScopePaymentEvent scopePaymentEvent = new ScopePaymentEvent(testView, 'X');
        assertEquals("ScopePaymentEvent,X", scopePaymentEvent.toString());

        assertNotNull(weaponSwapEvent.getPlayerColor());
        assertNotNull(weaponSwapEvent.getView());


    }

}
