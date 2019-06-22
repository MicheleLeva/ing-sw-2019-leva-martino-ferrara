package model.Weapons;

import model.Ammo;
import model.Model;
import model.cards.weapons.*;
import model.map.Map;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestWeapons {
    private Ammo pickUpCost = new Ammo(0,0,1);
    private Ammo baseCost = new Ammo(0,1,0);
    private Ammo alternativeCost = new Ammo(0,1,1);
    private Ammo optionalCost1 = new Ammo(1,0,0);
    private Ammo optionalCost2 = new Ammo(1,0,1);

    private Player player1 = new Player("player1", PlayerColor.BLUE);
    private Player player2 = new Player("player2",PlayerColor.YELLOW);
    private Player player3 = new Player("player3",PlayerColor.GREEN);
    private Model model ;
    private Map map;

    @Before
    public void initWeapons(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        this.model = new Model(players,5);
        model.setGameBoard(1);
        map = model.getGameBoard().getMap();
        player1.setPosition(map.getMap()[1][0]);
    }

    /**
     * tests the support methods for the Weapon class
     */
    @Test
    public void testWeapon(){
        Electroscythe electroscythe = new Electroscythe("ELECTROSCYTHE",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        electroscythe.setWeaponTree(new WeaponTree("src/resources/Electroscythe.json"));

        Cyberblade cyberblade = new Cyberblade("CYBERBLADE",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        cyberblade.setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));

        assertEquals(1,electroscythe.getDamageForUse("base",electroscythe));
        assertEquals(2,electroscythe.getDamageForUse("alternative",electroscythe));
        assertEquals(2,cyberblade.getDamageForUse("optional1",cyberblade));
        assertEquals(3,cyberblade.getDamageForUse("optional2",cyberblade));

        assertEquals(1,electroscythe.getMarksForUse("base",electroscythe));
        assertEquals(2,electroscythe.getMarksForUse("alternative",electroscythe));
        assertEquals(2,cyberblade.getMarksForUse("optional1",cyberblade));
        assertEquals(3,cyberblade.getMarksForUse("optional2",cyberblade));

        electroscythe.endAskSquares(player1,new ArrayList<>(),"base");
        assertEquals(1,model.getCurrent().getBaseCounter());
        electroscythe.endAskSquares(player1,new ArrayList<>(),"alternative");
        assertEquals(1,model.getCurrent().getAlternativeCounter());
        electroscythe.endAskSquares(player1,new ArrayList<>(),"optional1");
        assertEquals(1,model.getCurrent().getOptionalCounter1());
        electroscythe.endAskSquares(player1,new ArrayList<>(),"optional2");
        assertEquals(1,model.getCurrent().getOptionalCounter2());
        model.getCurrent().setSelectedWeapon(electroscythe);
        electroscythe.endAskTargets(player1,new ArrayList<>(),electroscythe,"base");
        assertEquals(2,model.getCurrent().getBaseCounter());
        electroscythe.endAskTargets(player1,new ArrayList<>(),electroscythe,"alternative");
        assertEquals(2,model.getCurrent().getAlternativeCounter());
        model.getCurrent().setSelectedWeapon(cyberblade);
        electroscythe.endAskTargets(player1,new ArrayList<>(),cyberblade,"optional1");
        assertEquals(2,model.getCurrent().getOptionalCounter1());
        electroscythe.endAskTargets(player1,new ArrayList<>(),cyberblade,"optional2");
        assertEquals(2,model.getCurrent().getOptionalCounter2());



    }

    /**
     * tests that all methods in the Heatseeker weapon execute without interruption
     */
    @Test
    public void testHeatseeker(){
        Heatseeker heatseeker = new Heatseeker("HEATSEEKER",pickUpCost,baseCost,1,1,1,model);
        heatseeker.setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));
        model.getCurrent().setSelectedWeapon(heatseeker);
        heatseeker.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        heatseeker.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());

    }

    /**
     * tests that all methods in the Cyberblade weapon execute without interruption
     */
    @Test
    public void testCyberblade(){
        Cyberblade cyberblade = new Cyberblade("CYBERBLADE",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        cyberblade.setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        cyberblade.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        player3.setPosition(map.getMap()[1][0]);
        cyberblade.askBaseRequirements(player1);
        cyberblade.askOptionalRequirements1(player1);
        cyberblade.askOptionalRequirements1(player1);
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        cyberblade.askOptionalRequirements2(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        cyberblade.askOptionalRequirements2(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        cyberblade.useBaseFireMode(player1,selectedTargets);
        cyberblade.useOptionalFireMode1(player1,selectedTargets);
        cyberblade.useOptionalFireMode2(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Whisper weapon execute without interruption
     */
    @Test
    public void testWhisper(){
        Whisper whisper = new Whisper("WHISPER",pickUpCost,baseCost,1,1,1,model);
        whisper.setWeaponTree(new WeaponTree("src/resources/Whisper.json"));
        model.getCurrent().setSelectedWeapon(whisper);
        player3.setPosition(map.getMap()[1][1]);
        whisper.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        assertEquals(1,model.getCurrent().getBaseCounter());
        whisper.askBaseRequirements(player1);
    }

    /**
     * tests that all methods in the Electroscythe weapon execute without interruption
     */
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
        assertEquals(0,model.getCurrent().getBaseCounter());
        electroscythe.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        electroscythe.useBaseFireMode(player1,selectedTargets);
        electroscythe.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the ZX2 weapon execute without interruption
     */
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
        assertEquals(0,model.getCurrent().getBaseCounter());
        zx2.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        zx2.useBaseFireMode(player1,selectedTargets);
        zx2.askAlternativeRequirements(player1);
        zx2.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        zx2.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the LockRifle weapon execute without interruption
     */
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
        assertEquals(1,model.getCurrent().getBaseCounter());
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        lockrifle.askOptionalRequirements1(player1);
        lockrifle.askOptionalRequirements1(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter1());
    }

    /**
     * tests that all methods in the vortexcannon weapon execute without interruption
     */
    @Test
    public void testVortexCannon(){
        Vortexcannon vortex = new Vortexcannon("VORTEX",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        vortex.setWeaponTree(new WeaponTree("src/resources/Vortexcannon.json"));
        player2.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedWeapon(vortex);
        vortex.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][1]);
        vortex.askBaseRequirements(player1);
        assertEquals(2,model.getCurrent().getBaseCounter());
        vortex.askBaseRequirements(player1);
        assertEquals(2,model.getCurrent().getBaseCounter());
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        vortex.askOptionalRequirements1(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter1());
        vortex.askOptionalRequirements1(player1);
    }

    /**
     * tests that all methods in the grenadeLauncher weapon execute without interruption
     */
    @Test
    public void testGrenadeLauncher(){
        Grenadelauncher grenadelauncher = new Grenadelauncher("GRENADELAUNCHER",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        grenadelauncher.setWeaponTree(new WeaponTree("src/resources/Grenadelauncher.json"));
        model.getCurrent().setSelectedWeapon(grenadelauncher);
        grenadelauncher.askBaseRequirements(player1);
        player2.setPosition(map.getMap()[1][1]);
        grenadelauncher.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][1]);
        model.getCurrent().incrementBaseCounter();
        grenadelauncher.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        grenadelauncher.askBaseRequirements(player1);
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        grenadelauncher.askOptionalRequirements1(player1);
        model.getCurrent().incrementOptionalCounter1();
        grenadelauncher.useBaseFireMode(player1,selectedTargets);
        grenadelauncher.useOptionalFireMode1(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Thor weapon execute without interruption
     */
    @Test
    public void testThor(){
        Thor thor = new Thor("THOR",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        thor.setWeaponTree(new WeaponTree("src/resources/Thor.json"));
        model.getCurrent().setSelectedWeapon(thor);
        thor.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        assertEquals(1,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        thor.askBaseRequirements(player1);
        selectedTargets.add(player2);
        player2.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        thor.askOptionalRequirements1(player1);
        assertEquals(0,model.getCurrent().getOptionalCounter1());
        model.getCurrent().incrementOptionalCounter1();
        thor.askOptionalRequirements1(player1);
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedOptionalTargets1(selectedTargets);
        thor.askOptionalRequirements2(player1);
        assertEquals(0,model.getCurrent().getOptionalCounter2());
        model.getCurrent().incrementOptionalCounter2();
        thor.askOptionalRequirements2(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        selectedTargets.clear();
        thor.useBaseFireMode(player1,selectedTargets);
        thor.useOptionalFireMode1(player1,selectedTargets);
        thor.useOptionalFireMode2(player1,selectedTargets);
    }

    /**
     * tests that all methods in the PlasmaGun weapon execute without interruption
     */
    @Test
    public void testPlasmaGun(){
        Plasmagun plasmagun = new Plasmagun("PLASMAGUN",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        plasmagun.setWeaponTree(new WeaponTree("src/resources/Plasmagun.json"));
        model.getCurrent().setSelectedWeapon(plasmagun);
        plasmagun.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        plasmagun.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        player3.setPosition(map.getMap()[1][0]);
        plasmagun.askBaseRequirements(player1);
        plasmagun.askOptionalRequirements1(player1);
        plasmagun.askOptionalRequirements1(player1);
        assertEquals(0,model.getCurrent().getOptionalCounter1());
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        plasmagun.askOptionalRequirements2(player1);
        plasmagun.askOptionalRequirements2(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter2());
        selectedTargets.clear();
        plasmagun.useBaseFireMode(player1,selectedTargets);
        plasmagun.useOptionalFireMode1(player1,selectedTargets);
        plasmagun.useOptionalFireMode2(player1,selectedTargets);
    }

    /**
     * tests that all methods in the RocketLauncher weapon execute without interruption
     */
    @Test
    public void testRocketLauncher(){
        Rocketlauncher rocketlauncher = new Rocketlauncher("ROCKETLAUCHER",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        rocketlauncher.setWeaponTree(new WeaponTree("src/resources/Rocketlauncher.json"));
        model.getCurrent().setSelectedWeapon(rocketlauncher);
        rocketlauncher.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        rocketlauncher.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        player3.setPosition(map.getMap()[1][0]);
        rocketlauncher.askBaseRequirements(player1);
        rocketlauncher.askOptionalRequirements1(player1);
        rocketlauncher.askOptionalRequirements1(player1);
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        rocketlauncher.askOptionalRequirements2(player1);
        rocketlauncher.askOptionalRequirements2(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter2());
        selectedTargets.clear();
        model.getCurrent().getSelectedBaseTargets().add(player2);
        rocketlauncher.useBaseFireMode(player1,selectedTargets);
        rocketlauncher.useOptionalFireMode1(player1,selectedTargets);
        rocketlauncher.useOptionalFireMode2(player1,selectedTargets);
    }


    /**
     * tests that all methods in the MachineGun weapon execute without interruption
     */
    @Test
    public void testMachineGun(){
        Machinegun machinegun = new Machinegun("MACHINEGUN",pickUpCost,baseCost,optionalCost1,optionalCost2,
                1,2,3,1,2,3,
                1,2,3,model);
        machinegun.setWeaponTree(new WeaponTree("src/resources/Machinegun.json"));
        model.getCurrent().setSelectedWeapon(machinegun);
        player2.setPosition(map.getMap()[1][1]);
        player3.setPosition(map.getMap()[1][1]);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        selectedTargets.add(player2);
        selectedTargets.add(player3);
        machinegun.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        machinegun.askOptionalRequirements1(player1);
        machinegun.askOptionalRequirements1(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter1());
        machinegun.askOptionalRequirements2(player1);
        machinegun.askOptionalRequirements2(player1);
        assertEquals(1,model.getCurrent().getOptionalCounter2());
        machinegun.useBaseFireMode(player1,new ArrayList<>());
        machinegun.useOptionalFireMode1(player1,new ArrayList<>());
        machinegun.useOptionalFireMode2(player1,new ArrayList<>());
    }

    /**
     * tests that all methods in the Hellion weapon execute without interruption
     */
    @Test
    public void testHellion(){
        Hellion hellion = new Hellion("HELLION",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        hellion.setWeaponTree(new WeaponTree("src/resources/Hellion.json"));
        model.getCurrent().setSelectedWeapon(hellion);
        player2.setPosition(map.getMap()[1][1]);
        hellion.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.clear();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        //hellion.askBaseRequirements(player1);
        hellion.askAlternativeRequirements(player1);
        assertEquals(1,model.getCurrent().getAlternativeCounter());
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.clear();
        selectedTargets.add(player3);
        player2.setPosition(null);
        hellion.useBaseFireMode(player1,selectedTargets);
        hellion.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Shotgun weapon execute without interruption
     */
    @Test
    public void testShotgun(){
        Shotgun shotgun = new Shotgun("SHOTGUN",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        shotgun.setWeaponTree(new WeaponTree("src/resources/Shotgun.json"));
        model.getCurrent().setSelectedWeapon(shotgun);
        player2.setPosition(map.getMap()[1][1]);
        shotgun.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        ArrayList<Player> selectedTargets = new ArrayList<>();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shotgun.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        selectedTargets.clear();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shotgun.askBaseRequirements(player1);
        shotgun.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        shotgun.useBaseFireMode(player1,selectedTargets);
        shotgun.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Furnace weapon execute without interruption
     */
    @Test
    public void testFurnace(){
        Furnace furnace = new Furnace("SHOTGUN",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        furnace.setWeaponTree(new WeaponTree("src/resources/Furnace.json"));
        model.getCurrent().setSelectedWeapon(furnace);
        furnace.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        player2.setPosition(map.getMap()[0][1]);
        furnace.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        furnace.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        furnace.askBaseRequirements(player1);
        furnace.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        furnace.useBaseFireMode(player1,selectedTargets);
        furnace.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Shockwave weapon execute without interruption
     */
    @Test
    public void testShockwave(){
        Shockwave shockwave = new Shockwave("SHOCKWAVE",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        shockwave.setWeaponTree(new WeaponTree("src/resources/Shockwave.json"));
        ArrayList<Player> selectedTargets = new ArrayList<>();
        player2.setPosition(map.getMap()[1][0]);
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedWeapon(shockwave);
        shockwave.askBaseRequirements(player1);
        player2.setPosition(map.getMap()[1][0]);
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shockwave.askBaseRequirements(player1);
        assertEquals(0,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shockwave.askBaseRequirements(player1);
        model.getCurrent().decrementBaseCounter();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shockwave.askBaseRequirements(player1);
        assertEquals(3,model.getCurrent().getBaseCounter());
        selectedTargets.clear();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        player2.setPosition(map.getMap()[1][0]);
        player3.setPosition(map.getMap()[1][1]);
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        shockwave.askAlternativeRequirements(player1);
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        shockwave.useBaseFireMode(player1,selectedTargets);
        shockwave.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the Railgun weapon execute without interruption
     */
    @Test
    public void testRailgun(){
        RailGun railGun = new RailGun("RAILGUN",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        railGun.setWeaponTree(new WeaponTree("src/resources/Railgun.json"));
        ArrayList<Player> selectedTargets = new ArrayList<>();
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedWeapon(railGun);
        railGun.askBaseRequirements(player1);
        railGun.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        selectedTargets.clear();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        railGun.askAlternativeRequirements(player1);
        assertEquals(1,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedAlternativeTargets(selectedTargets);
        railGun.askAlternativeRequirements(player1);
        assertEquals(2,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        railGun.useBaseFireMode(player1,selectedTargets);
        railGun.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the SledgeHammer weapon execute without interruption
     */
    @Test
    public void testSledgeHammer(){
        Sledgehammer sledgehammer = new Sledgehammer("SLEDGEHAMMER",pickUpCost,baseCost,alternativeCost,
                1,2,1,2,2,1,model);
        sledgehammer.setWeaponTree(new WeaponTree("src/resources/Sledgehammer.json"));
        ArrayList<Player> selectedTargets = new ArrayList<>();
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedWeapon(sledgehammer);
        sledgehammer.askBaseRequirements(player1);
        model.getCurrent().incrementBaseCounter();
        sledgehammer.askBaseRequirements(player1);
        model.getCurrent().incrementBaseCounter();
        selectedTargets.clear();
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        selectedTargets.add(player2);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        sledgehammer.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        model.getCurrent().incrementAlternativeCounter();
        sledgehammer.askAlternativeRequirements(player1);
        model.getCurrent().incrementAlternativeCounter();
        model.getCurrent().incrementAlternativeCounter();
        selectedTargets.clear();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedAlternativeTargets(selectedTargets);
        sledgehammer.askAlternativeRequirements(player1);
        assertEquals(3,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        sledgehammer.useBaseFireMode(player1,selectedTargets);
        selectedTargets.clear();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedAlternativeTargets(selectedTargets);
        sledgehammer.useAlternativeFireMode(player1,selectedTargets);
    }

    /**
     * tests that all methods in the TractorBeam weapon execute without interruption
     */
    @Test
    public void testTractorBeam() {
        Tractorbeam tractorbeam = new Tractorbeam("TRACTORBEAM", pickUpCost, baseCost, alternativeCost,
                1, 2, 1, 2, 2, 1, model);
        tractorbeam.setWeaponTree(new WeaponTree("src/resources/Tractorbeam.json"));
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedWeapon(tractorbeam);
        tractorbeam.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        tractorbeam.askBaseRequirements(player1);
        tractorbeam.askBaseRequirements(player1);
        assertEquals(2,model.getCurrent().getBaseCounter());
        tractorbeam.askAlternativeRequirements(player1);
        tractorbeam.askAlternativeRequirements(player1);
        assertEquals(1,model.getCurrent().getAlternativeCounter());
        tractorbeam.useBaseFireMode(player1, selectedTargets);
        tractorbeam.useAlternativeFireMode(player1, selectedTargets);
    }

    /**
     * tests that all methods in the PowerGlove weapon execute without interruption
     */
    @Test
    public void testPowerGlove(){
        Powerglove powerglove = new Powerglove("POWERGLOVE", pickUpCost, baseCost, alternativeCost,
                1, 2, 1, 2, 2, 1, model);
        powerglove.setWeaponTree(new WeaponTree("src/resources/Powerglove.json"));
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedWeapon(powerglove);
        powerglove.askBaseRequirements(player1);
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        model.getCurrent().incrementBaseCounter();
        powerglove.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        player1.setPosition(map.getMap()[1][1]);
        powerglove.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        model.getCurrent().incrementAlternativeCounter();
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][0]);
        powerglove.askAlternativeRequirements(player1);
        model.getCurrent().incrementAlternativeCounter();
        model.getCurrent().incrementAlternativeCounter();
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][1]);
        powerglove.askAlternativeRequirements(player1);
        assertEquals(6,model.getCurrent().getAlternativeCounter());
        model.getCurrent().decrementAlternativeCounter();
        powerglove.askAlternativeRequirements(player1);
        powerglove.askAlternativeRequirements(player1);
        assertEquals(5,model.getCurrent().getAlternativeCounter());
        selectedTargets.clear();
        selectedTargets.add(player3);
        player3.setPosition(map.getMap()[1][1]);
        model.getCurrent().setSelectedAlternativeTargets(selectedTargets);
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][1]);
        powerglove.askAlternativeRequirements(player1);
        powerglove.useBaseFireMode(player1, selectedTargets);
        powerglove.useAlternativeFireMode(player1, selectedTargets);
    }

    /**
     * tests that all methods in the FlameThrower weapon execute without interruption
     */
    @Test
    public void testFlamethrower(){
        Flamethrower flamethrower = new Flamethrower("FLAMETHROWER", pickUpCost, baseCost, alternativeCost,
                1, 2, 1, 2, 2, 1, model);
        flamethrower.setWeaponTree(new WeaponTree("src/resources/Flamethrower.json"));
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedWeapon(flamethrower);
        player3.setPosition(map.getMap()[1][0]);
        flamethrower.askBaseRequirements(player1);
        assertEquals(1,model.getCurrent().getBaseCounter());
        selectedTargets.add(player3);
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        flamethrower.askBaseRequirements(player1);
        assertEquals(2,model.getCurrent().getBaseCounter());
        model.getCurrent().incrementBaseCounter();
        flamethrower.askBaseRequirements(player1);
        player3.setPosition(null);
        flamethrower.askAlternativeRequirements(player1);
        assertEquals(0,model.getCurrent().getAlternativeCounter());
        model.getCurrent().incrementAlternativeCounter();
        model.getCurrent().setSelectedWeaponSquare(map.getMap()[1][1]);
        flamethrower.askAlternativeRequirements(player1);
        assertEquals(1,model.getCurrent().getAlternativeCounter());
    }


}
