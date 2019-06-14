package model.exchanges;

import model.exchanges.messages.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMessages {

    @Test
    public void testMessages(){
        AskFireModesMessage askFireModesMessage = new AskFireModesMessage("a");
        assertEquals("AskFireModesMessage,a", askFireModesMessage.toString());

        AskReloadMessage askReloadMessage = new AskReloadMessage("a");
        assertEquals("AskReloadMessage,a", askReloadMessage.toString());

        ChooseActionMessage chooseActionMessage = new ChooseActionMessage("a");
        assertEquals("ChooseActionMessage,a", chooseActionMessage.toString());

        ChooseNewtonOpponentMessage chooseNewtonOpponentMessage = new ChooseNewtonOpponentMessage("a");
        assertEquals("ChooseNewtonOpponentMessage,a", chooseNewtonOpponentMessage.toString());

        ChooseNewtonSquareMessage chooseNewtonSquareMessage = new ChooseNewtonSquareMessage("a");
        assertEquals("ChooseNewtonSquareMessage,a", chooseNewtonSquareMessage.toString());

        ChoosePowerUpMessage choosePowerUpMessage = new ChoosePowerUpMessage("a");
        assertEquals("ChoosePowerUpMessage,a", choosePowerUpMessage.toString());

        ChooseTeleporterSquareMessage chooseTeleporterSquareMessage = new ChooseTeleporterSquareMessage("a");
        assertEquals("ChooseTeleporterSquareMessage,a", chooseTeleporterSquareMessage.toString());

        ChooseWeaponSquareMessage chooseWeaponSquareMessage = new ChooseWeaponSquareMessage("a");
        assertEquals("ChooseWeaponSquareMessage,a", chooseWeaponSquareMessage.toString());

        DiscardPowerUpMessage discardPowerUpMessage = new DiscardPowerUpMessage("a");
        assertEquals("DiscardPowerUpMessage,a", discardPowerUpMessage.toString());

        GenericMessage genericMessage = new GenericMessage("a");
        assertEquals("GenericMessage,a", genericMessage.toString());

        PickUpPaymentMessage pickUpPaymentMessage = new PickUpPaymentMessage("a", 1);
        assertEquals("PickUpPaymentMessage,a,1", pickUpPaymentMessage.toString());

        ReloadPaymentMessage reloadPaymentMessage = new ReloadPaymentMessage("a", 1);
        assertEquals("ReloadPaymentMessage,a,1", reloadPaymentMessage.toString());

        RunMessage runMessage = new RunMessage("a");
        assertEquals("RunMessage,a", runMessage.toString());

        SetAfkMessage setAfkMessage = new SetAfkMessage("a");
        assertEquals("SetAfkMessage,a", setAfkMessage.toString());

        ShootMessage shootMessage = new ShootMessage("a");
        assertEquals("ShootMessage,a", shootMessage.toString());

        ShowPickUpWeaponsMessage showPickUpWeaponsMessage = new ShowPickUpWeaponsMessage("a");
        assertEquals("ShowPickUpWeaponsMessage,a", showPickUpWeaponsMessage.toString());

        ShowWeaponCardsMessage showWeaponCardsMessage = new ShowWeaponCardsMessage("a");
        assertEquals("ShowWeaponCardsMessage,a", showWeaponCardsMessage.toString());

        TagbackGrenadeMessage tagbackGrenadeMessage = new TagbackGrenadeMessage("a");
        assertEquals("TagbackGrenadeMessage,a",tagbackGrenadeMessage.toString());

        TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage("a");
        assertEquals("TargetingScopeMessage,a", targetingScopeMessage.toString());

        TargetingScopeSelectionMessage targetingScopeSelectionMessage = new TargetingScopeSelectionMessage("a");
        assertEquals("TargetingScopeSelectionMessage,a", targetingScopeSelectionMessage.toString());

        TargetsSelectionMessage targetsSelectionMessage = new TargetsSelectionMessage("a", 1);
        assertEquals("TargetsSelectionMessage,a,1", targetsSelectionMessage.toString());

        VoteMapMessage voteMapMessage = new VoteMapMessage("a");
        assertEquals("VoteMapMessage,a", voteMapMessage.toString());

        WeaponPaymentMessage weaponPaymentMessage = new WeaponPaymentMessage("a", 1);
        assertEquals("WeaponPaymentMessage,a,1", weaponPaymentMessage.toString());

        WeaponReloadMessage weaponReloadMessage = new WeaponReloadMessage("a");
        assertEquals("WeaponReloadMessage,a", weaponReloadMessage.toString());

        WeaponSwapMessage weaponSwapMessage = new WeaponSwapMessage("a");
        assertEquals("WeaponSwapMessage,a", weaponSwapMessage.toString());

    }
}
