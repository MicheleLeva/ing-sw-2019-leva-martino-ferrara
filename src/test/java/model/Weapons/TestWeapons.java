package model.Weapons;

import model.Ammo;
import model.Model;
import model.cards.weapons.*;
import model.map.Map;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestWeapons {
    Ammo pickUpCost = new Ammo(0,0,1);
    Ammo baseCost = new Ammo(0,1,0);
    Ammo alternativeCost = new Ammo(0,1,1);
    Ammo optionalCost1 = new Ammo(1,0,0);
    Ammo optionalCost2 = new Ammo(1,0,1);

    Player player1 = new Player("player1", PlayerColor.BLUE);
    Player player2 = new Player("player2",PlayerColor.YELLOW);
    Player player3 = new Player("player3",PlayerColor.GREEN);
    Model model ;
    Map map;

    @Before
    public void initWeapons(){
        ArrayList<Player> players = new ArrayList<>();
        map = new Map(1);
        player1.setPosition(map.getMap()[1][0]);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        this.model = new Model(players,5);

    }

    @Test
    public void testHeatseeker(){
        Heatseeker heatseeker = new Heatseeker("HEATSEEKER",pickUpCost,baseCost,1,1,1,model);
        heatseeker.setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));
        model.getCurrent().setSelectedWeapon(heatseeker);
        heatseeker.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        heatseeker.askBaseRequirements(player1);
    }

    @Test
    public void testCyberblade(){
        Cyberblade cyberblade = new Cyberblade("CYBERBLADE",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        cyberblade.setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        cyberblade.askBaseRequirements(player1);
        player3.setPosition(map.getMap()[1][0]);
        cyberblade.askBaseRequirements(player1);
        cyberblade.askOptionalRequirements1(player1);
        cyberblade.askOptionalRequirements1(player1);
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        cyberblade.askOptionalRequirements2(player1);
        cyberblade.askOptionalRequirements2(player1);
        selectedTargets.clear();
        cyberblade.useBaseFireMode(player1,selectedTargets);
        cyberblade.useOptionalFireMode1(player1,selectedTargets);
        cyberblade.useOptionalFireMode2(player1,selectedTargets);
    }

    @Test
    public void testWhisper(){
        Whisper whisper = new Whisper("WHISPER",pickUpCost,baseCost,1,1,1,model);
        whisper.setWeaponTree(new WeaponTree("src/resources/Whisper.json"));
        model.getCurrent().setSelectedWeapon(whisper);
        player3.setPosition(map.getMap()[1][1]);
        whisper.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        whisper.askBaseRequirements(player1);
    }

    @Test
    public void testElectroscythe(){
        Electroscythe electroscythe = new Electroscythe("ELECTROSCYTHE",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        electroscythe.setWeaponTree(new WeaponTree("src/resources/Electroscythe.json"));
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        electroscythe.askBaseRequirements(player1);
        electroscythe.askAlternativeRequirements(player1);
        electroscythe.useBaseFireMode(player1,selectedTargets);
        electroscythe.useAlternativeFireMode(player1,selectedTargets);
    }

    @Test
    public void testZX2(){
        ZX2 zx2 = new ZX2("ZX2",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        zx2.setWeaponTree(new WeaponTree("src/resources/Zx2.json"));
        model.getCurrent().setSelectedWeapon(zx2);
        zx2.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        zx2.askBaseRequirements(player1);
        zx2.askBaseRequirements(player1);
        zx2.useBaseFireMode(player1,selectedTargets);
        zx2.askAlternativeRequirements(player1);
        zx2.askAlternativeRequirements(player1);
        zx2.useAlternativeFireMode(player1,selectedTargets);
    }

    @Test
    public void testLockRifle(){
        LockRifle lockrifle = new LockRifle("LOCKRIFLE",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        lockrifle.setWeaponTree(new WeaponTree("src/resources/Lockrifle.json"));
        model.getCurrent().setSelectedWeapon(lockrifle);
        lockrifle.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        lockrifle.askBaseRequirements(player1);
        lockrifle.askBaseRequirements(player1);
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        lockrifle.askOptionalRequirements1(player1);
        lockrifle.askOptionalRequirements1(player1);
    }
}
