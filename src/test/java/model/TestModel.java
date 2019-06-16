package model;

import model.cards.AmmoColor;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.cards.powerups.TargetingScope;
import model.cards.powerups.Teleporter;
import model.cards.weapons.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import model.turn.TurnManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static model.Model.runnableSquare;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tests if the returned squares are actually those at distance 2 in all cardinal directions
 */
public class TestModel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model model ;
    private model.map.Map map ;
    private Square[][] squares;

    @Before
    /**
     * initializes the variables needed for the tests
     */
    public void setUp() throws Exception {
        //map = new model.map.Map(1);
        player1 = new Player("player1",PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        model = new Model(players,8);
        model.setGameBoard(1);
        squares = model.getGameBoard().getMap().getMap();
        map = model.getGameBoard().getMap();
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[1][2]);

    }

    @Test
    /**
     * tests if the returned players are those tha are visible from the current player
     */
    public void testVisiblePlayers(){
        ArrayList<Player> visiblePlayers;
        visiblePlayers = model.getVisiblePlayers(player1);
        assertTrue(visiblePlayers.contains(player2));
        assertTrue(visiblePlayers.contains(player3));


    }

    @Test
    /**
     * tests if the returned players are those that are not in the same room of the current player
     */
    public void testPlayersNotInYourRoom(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[0][0]);

        visiblePlayers = model.getPlayersNotInYourRoom(player1);
        assertTrue(visiblePlayers.contains(player3));
        assertEquals(visiblePlayers.size(), 1);
    }

    @Test
    /**
     * tests if the returned players are thos at distance inferior to the given one
     */
    public void testPlayersAtDistance(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][2]);

        visiblePlayers = model.getPlayersAtDistance(3,player1);
        assertTrue(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),2);
    }

    @Test
    /**
     * tests if the returned players are those at a distance superior to the given one
     */
    public void testPlayersAtDistanceMore(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][3]);

        visiblePlayers = model.getPlayersAtDistanceMore(3,player1);
        System.out.println(visiblePlayers+"\n");
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    /**
     * tests if the returned players are those in the same square of the current player
     */
    public void testPlayersInSameSquare(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][0]);
        player3.setPosition(squares[1][3]);

        visiblePlayers = model.getPlayersInSameSquare(player1);
        System.out.println(visiblePlayers+"\n");
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    /**
     * tests if the returned squares are actually those at distance 1 in all cardinal directions
     */
    public void testNonVisiblePlayers(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getNonVisiblePlayers(player1);
        System.out.println(visiblePlayers+"\n");
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    /**
     * tests if the returned squares are actually those at distance 2 in all cardinal directions
     */
    public void testSquaresInCardinal2(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][3]);

        visibleSquares = model.getSquaresInCardinal2(player1);
        //visibleSquares.stream().map(x->x.getID()).forEach(x->System.out.println(x));
        assertEquals(visibleSquares.size(),4);
    }

    @Test
    /**
     * tests if the returned players are actually those in all 4 cardinal directions
     */
    public void testPlayersInCardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersInCardinalDirection(player1);
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    /**
     * tests the returned squares are actually those i the selected cardinal direction
     */
    public void testPlayersInSelectedardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'e');
        assertFalse(visiblePlayers.contains(player3));
        assertTrue(visiblePlayers.contains(player2));
        assertEquals(visiblePlayers.size(),1);
    }

    @Test
    /**
     * tests if the returned visible squares from a player are correct
     */
    public void testGetVisibleSquares(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);
        visibleSquares = model.getVisibleSquares(player1);
        for(Square square : visibleSquares)
            System.out.println(square.getID());
        assertTrue(visibleSquares.contains(squares[0][0]));
        assertTrue(visibleSquares.contains(squares[0][1]));
        assertTrue(visibleSquares.contains(squares[0][2]));
        assertTrue(visibleSquares.contains(squares[1][0]));
        assertTrue(visibleSquares.contains(squares[1][1]));
        assertTrue(visibleSquares.contains(squares[1][2]));
        assertFalse(visibleSquares.contains(squares[2][2]));

    }

    @Test
    /**
     * tests if the returned squares at distance 1 in all 4 cardin directions are correct
     */
    public void testGetSquaresInCardinal1(){
        ArrayList<Square> visibleSquares;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][3]);
        player3.setPosition(squares[2][1]);

        visibleSquares = model.getSquaresInCardinal1(player1);
        assertTrue(visibleSquares.contains(squares[0][0]));
        assertTrue(visibleSquares.contains(squares[1][0]));
        assertTrue(visibleSquares.contains(squares[1][1]));
        assertFalse(visibleSquares.contains(squares[2][2]));
    }

    @Test
    /**
     * tests if the method returns players at the selected distance
     */
    public void testGetPlayersAtDistance(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersAtDistance(1,player1);
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
    }

    @Test
    /**
     * tests if the method returns players at the selected distance from a selected square
     */
    public void testGetPlayersAtDistanceFromSquare(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[2][1]);

        visiblePlayers = model.getPlayersAtDistance(1,player1,player1.getPosition());
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
    }

    @Test
    /**
     * tests if the method returns players in the selected cardinal direction
     */
    public void testGetPlayersInSeletedcardinal(){
        ArrayList<Player> visiblePlayers;
        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[1][1]);
        player3.setPosition(squares[0][0]);

        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'n');
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'e');
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[2][1]);
        player3.setPosition(squares[1][0]);
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'s');
        assertTrue(visiblePlayers.contains(player2));
        assertFalse(visiblePlayers.contains(player3));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'w');
        assertTrue(visiblePlayers.contains(player3));
        assertFalse(visiblePlayers.contains(player2));
        visiblePlayers = model.getPlayersInSelectedCardinal(player1,'x');
    }

    @Test
    /**
     * tests if the returned square for the power glove weapon is correct
     */
    public void testGetNextPowerGloveSquare(){
        Square square;
        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[1][2]);
        player3.setPosition(squares[2][1]);

        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == squares[1][3]);

        player1.setPosition(squares[1][2]);
        player2.setPosition(squares[1][1]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == squares[1][0]);

        player1.setPosition(squares[1][0]);
        player2.setPosition(squares[0][0]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == null);

        player1.setPosition(squares[1][1]);
        player2.setPosition(squares[2][1]);
        square = model.getNextPowerGloveSquare(player1.getPosition(),player2.getPosition());
        assertTrue(square == null);

    }

    @Test
    /**
     * tests if the squares selected by the BFS method runnable square are correct
     */
    public void testRunnableSquare(){

        ArrayList<Square> test = new ArrayList<>();

        Square firstSquare = map.getSquareFromCoordinates(0, 0);

        test.add(firstSquare);
        assertEquals(runnableSquare(0, firstSquare), test);

        test.add(map.getSquareFromCoordinates(0,1));
        test.add(map.getSquareFromCoordinates(1,0));
        assertTrue(test.containsAll(runnableSquare(1,firstSquare)));
        assertEquals(test.size(), runnableSquare(1,firstSquare).size());

        test.add(map.getSquareFromCoordinates(0, 2));
        test.add(map.getSquareFromCoordinates(1, 1));
        assertTrue(test.containsAll(runnableSquare(2, firstSquare)));
        assertEquals(test.size(), runnableSquare(2, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1, 2));
        test.add(map.getSquareFromCoordinates(2, 1));
        assertTrue(test.containsAll(runnableSquare(3, firstSquare)));
        assertEquals(test.size(), runnableSquare(3, firstSquare).size());

        test.add(map.getSquareFromCoordinates(1,3));
        test.add(map.getSquareFromCoordinates(2,2));
        assertTrue(test.containsAll(runnableSquare(4, firstSquare)));
        assertEquals(test.size(), runnableSquare(4, firstSquare).size());
    }

    /**
     * tests that the string with the weapon names is created
     */
    @Test
    public void testShowWeaponCards(){
        Ammo ammo = new Ammo(0,0,0);
        player1.getResources().addWeapon(new Heatseeker("heat",ammo,ammo,1,1,1,model));
        player1.getResources().getAllWeapon().get(0).reload();
    }

    /**
     * tests if the returned cost of the given fire mode is correct
     */
    @Test
    public void testFireCost(){
        Ammo ammo = new Ammo(0,0,0);
        Electroscythe electroscythe = new Electroscythe("electro",ammo,ammo,ammo,1,1,
                1,1,1,1,model);
        assertTrue(model.setFireCost("reload",electroscythe)==ammo);
        assertTrue(model.setFireCost("pickup",electroscythe)==ammo);
        assertTrue(model.setFireCost("alternative",electroscythe)==ammo);
        Cyberblade cyberblade = new Cyberblade("cyber",ammo,ammo,ammo,ammo,1,1,
                1,1,1,1,1,1,
                1,model);
        assertTrue(model.setFireCost("optional1",cyberblade)==ammo);
        assertTrue(model.setFireCost("optional2",cyberblade)==ammo);
        assertEquals(model.setFireCost("asdf",cyberblade).getRed(),0);
        assertEquals(model.setFireCost("asdf",cyberblade).getBlue(),0);
        assertEquals(model.setFireCost("asdf",cyberblade).getYellow(),0);

    }
    @Test
    /**
     * tests if the correct amount of damage is given
     */
    public void testAddDamage(){
        PowerUp tagabackGrenade = new TagbackGrenade(model,AmmoColor.BLUE);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(tagabackGrenade);
        player2.getResources().addPowerUp(powerUps);
        model.addDamage(player1.getPlayerColor(),player2.getPlayerColor(),1);
        assertEquals(player2.getPlayerBoard().getDamageCounter().getDamage(),1);
        model.addDamage(player1.getPlayerColor(),player2.getPlayerColor(),10);
        assertEquals(player2.getPlayerBoard().getDamageCounter().getDamage(),11);
        model.addDamage(player1.getPlayerColor(),player2.getPlayerColor(),1);
        assertEquals(player2.getPlayerBoard().getDamageCounter().getDamage(),12);
    }

    @Test
    /**
     * Tests if the correct number of marks are added
     */
    public void testAddMark(){
        model.addMark(player1.getPlayerColor(),player2.getPlayerColor(),1);
        assertEquals(player2.getPlayerBoard().getMarkCounter().getMarkFromColor(player1.getPlayerColor()),1);
    }

    @Test
    /**
     * tests that the action tree is managed correctly after updating the action
     */
    public void testVerifyNewAction(){
        model.verifyNewAction(player1.getPlayerColor());
        assertTrue(player1.getActionTree().getID()==1);
    }

    @Test
    /**
     * tests that all the layers in a match are returned correctly
     */
    public void testGetEachPlayer(){
        List<Player> eachPlayers = model.getEachPlayer();
        assertTrue(eachPlayers.containsAll(this.players));
    }

    @Test
    /**
     * tests that all damaged players are asked for the targeting scope powerUp
     */
    public void testTargetingScopeTargets(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player2);
        model.targetingScopeTargets(player1.getPlayerColor(),players);
    }

    /**
     * tests that the player can correctly be set as AFK and removed from the AFK players
     */
    @Test
    public void testAFK(){
        model.setPlayerAfk(player1);
        assertTrue(player1.isAfk());
        model.setPlayerAfk(player1);
        model.wakeUpPlayer(player1);
        assertTrue(!player1.isAfk());

    }

    /**
     * tests that the tagback grenade request is sent correctly
     */
    @Test
    public void testTagBackGrenadeRequest(){
        TagbackGrenade tagBack = new TagbackGrenade(model,AmmoColor.BLUE);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(tagBack);
        player2.getResources().addPowerUp(powerUps);
        model.tagbackGranadeRequest(player2,player1);
    }

    /**
     * tests that the map vote request is sent correctly
     */
    @Test
    public void testMapVote(){
        model.mapVote(player1);
    }


    @Test
    /**
     * Test that the string with the pickUp-able weapons is built correctly
     */
    public void testShowPickUpWeapons(){
        Ammo ammo = new Ammo(0,0,0);
        ArrayList<Weapon> weapons = new ArrayList<>();
        weapons.add(new Heatseeker("heat",ammo,ammo,1,1,1,model));
        model.showPickUpWeapons(weapons,player1.getPlayerColor());
    }


    @Test
    /**
     *tests that the selected weapons are correctly swapped
     */
    public void testSwapWeapon(){
        Ammo ammo = new Ammo(0,0,0);
        Heatseeker heatseeker = new Heatseeker("heat",ammo,ammo,1,1,1,model);
        Heatseeker heatseeker2 = new Heatseeker("heat",ammo,ammo,1,1,1,model);

        player1.getResources().addWeapon(heatseeker2);
        model.getCurrent().setSelectedPickUpWeapon(heatseeker);
        player1.getPosition().getWeapon()[0]=null;
        player1.getPosition().setWeapon(heatseeker);
        model.swapPickUpWeapon(player1,1);
        assertTrue(player1.getResources().getAllWeapon().contains(heatseeker));
        assertTrue(!player1.getResources().getAllWeapon().contains(heatseeker2));
        for(int i = 0; i<3;i++)
            System.out.println(i + player1.getPosition().getWeapon()[i].getWeaponName());
        assertTrue(player1.getPosition().getWeapon()[0]==heatseeker2);
        player1.getResources().getAllWeapon().get(0).unload();
        model.requestWeaponReload(player1.getPlayerColor());

    }


    @Test
    /**
     * tests that the payment request for reload works as intended
     */
    public void testAskReloadPayment(){
        Ammo ammo = new Ammo(1,0,0);
        Heatseeker heatseeker = new Heatseeker("heat",ammo,ammo,1,1,1,model);
        Teleporter teleporter = new Teleporter(model,AmmoColor.BLUE);
        model.askReloadPayment(player1,heatseeker);
        model.addAmmo(player1.getPlayerColor(),new Ammo(1,0,0));
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(teleporter);
        player1.getResources().addPowerUp(powerUps);
        model.askReloadPayment(player1,heatseeker);
        assertEquals(0, player1.getResources().getAvailableAmmo().getRed());
        powerUps.clear();
        Teleporter teleporter2 = new Teleporter(model,AmmoColor.RED);
        ArrayList<PowerUp> powerUps2 = new ArrayList<>();
        powerUps2.add(teleporter2);
        player1.getResources().addPowerUp(powerUps2);
        model.askReloadPayment(player1,heatseeker);
        assertEquals(0, player1.getResources().getAvailableAmmo().getRed());
    }

    @Test
    /**
     * tests that the payment request for pickUp works as intended
     */
    public void testAskPickUpPayment(){
        Ammo ammo = new Ammo(1,0,0);
        Heatseeker heatseeker = new Heatseeker("heat",ammo,ammo,1,1,1,model);
        Teleporter teleporter = new Teleporter(model,AmmoColor.BLUE);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(teleporter);
        player1.getResources().addPowerUp(powerUps);
        model.askPickUpPayment(player1,heatseeker);
        assertEquals(0, player1.getResources().getAvailableAmmo().getRed());
        powerUps.clear();
        Teleporter teleporter2 = new Teleporter(model,AmmoColor.RED);
        ArrayList<PowerUp> powerUps2 = new ArrayList<>();
        powerUps2.add(teleporter2);
        player1.getResources().addPowerUp(powerUps2);
        model.askPickUpPayment(player1,heatseeker);
        assertEquals(0, player1.getResources().getAvailableAmmo().getRed());
    }

    @Test
    /**
     * tests that the payment request for pickUp works as intended
     */
    public void testAskFireModePayment(){
        Ammo ammo = new Ammo(1,0,0);
        Hellion hellion = new Hellion("hellion",ammo,ammo,ammo,1,1,
                1,1,1,1,model);
        hellion.setWeaponTree(new WeaponTree("src/resources/Hellion.json"));
        model.getCurrent().setSelectedWeapon(hellion);
        Teleporter teleporter = new Teleporter(model,AmmoColor.BLUE);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(teleporter);
        player1.getResources().addPowerUp(powerUps);
        model.askFireModePayment(player1,hellion,"alternative");
        assertEquals(1, player1.getResources().getAvailableAmmo().getRed());
        powerUps.clear();
        Teleporter teleporter2 = new Teleporter(model,AmmoColor.RED);
        ArrayList<PowerUp> powerUps2 = new ArrayList<>();
        powerUps2.add(teleporter2);
        player1.getResources().addPowerUp(powerUps2);
        model.askFireModePayment(player1,hellion,"alternative");
        Machinegun machinegun = new Machinegun("hellion",ammo,ammo,ammo,ammo,1,1,
                1,1,1,1, 1,1,
                1,model);
        machinegun.setWeaponTree(new WeaponTree("src/resources/Machinegun.json"));
        model.askFireModePayment(player1,machinegun,"optional1");
        model.askFireModePayment(player1,machinegun,"optional2");
        player1.getResources().getPowerUp().clear();
        model.askFireModePayment(player1,machinegun,"optional1");
        model.askFireModePayment(player1,machinegun,"optional2");
        model.notifyShoot(player1);
    }

    /**
     * tests next available action for selected weapon
     */
    @Test
    public void testCheckNextWeaponAction(){
        Ammo ammo = new Ammo(1,0,0);
        Heatseeker heatseeker = new Heatseeker("heat",ammo,ammo,1,1,1,model);
        heatseeker.setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));
        model.checkNextWeaponAction(heatseeker,player1,new ArrayList<>());
        TargetingScope targetingScope = new TargetingScope(model,AmmoColor.BLUE);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        powerUps.add(targetingScope);
        player1.getResources().addPowerUp(powerUps);
        System.out.println(heatseeker.getWeaponTree().getLastAction().getData().getType());
        heatseeker.getWeaponTree().updateLastAction(0);
        System.out.println(heatseeker.getWeaponTree().getLastAction().getData().getType());
        System.out.println(heatseeker.getWeaponTree().getLastActionPerformed().getData().getType());
        model.checkNextWeaponAction(heatseeker,player1,new ArrayList<>());
        System.out.println(heatseeker.getWeaponTree().isActionEnded());
        System.out.println("PROVA");

    }


    @Test
    /**
     *  tests that the correct amount of ammo is added to the player's resources
     */
    public void testAddAmmo(){
        model.addAmmo(player1.getPlayerColor(),new Ammo(1,1,0));
        assertEquals(player1.getResources().getAvailableAmmo().getRed(),2);
        assertEquals(player1.getResources().getAvailableAmmo().getBlue(),2);
        assertEquals(player1.getResources().getAvailableAmmo().getYellow(),1);

    }

    @Test
    /**
     *  tests that Ammo Card is correctly picked up from the current square
     */
    public void testDiscardAmmo(){
        player1.setPosition(squares[1][1]);
        model.discardAmmo(squares[1][1]);
        assertTrue(squares[1][1].isEmpty());
    }

    @Test
    public void testDrawPowerUp(){
        model.drawPowerUp(player1.getPlayerColor(),1);
        assertEquals(1, player1.getResources().getPowerUp().size());
    }

    @Test
    public void testSelectTargets(){
        Ammo ammo = new Ammo(0,0,0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player2);
        Electroscythe electroscythe = new Electroscythe("heat",ammo,ammo,ammo,1,1,
                1,1,1,1,model);
        electroscythe.setWeaponTree(new WeaponTree("src/resources/Electroscythe.json"));
        model.getCurrent().setSelectedWeapon(electroscythe);
        model.selectTargets(player1.getPlayerColor(),new ArrayList<>(),1);
        electroscythe.getWeaponTree().updateLastAction(0);
        assertEquals("base",electroscythe.getWeaponTree().getLastAction().getData().getType());
        model.selectTargets(player1.getPlayerColor(),new ArrayList<>(),1);
        electroscythe.getWeaponTree().resetAction();
        electroscythe.getWeaponTree().updateLastAction(1);
        assertEquals("alternative",electroscythe.getWeaponTree().getLastAction().getData().getType());
        model.selectTargets(player1.getPlayerColor(),new ArrayList<>(),1);
        Cyberblade cyberblade = new Cyberblade("hellion",ammo,ammo,ammo,ammo,1,1,
                1,1,1,1, 1,1,
                1,model);
        cyberblade.setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
        model.getCurrent().setSelectedWeapon(cyberblade);
        cyberblade.getWeaponTree().resetAction();
        cyberblade.getWeaponTree().updateLastAction(1);
        assertEquals("optional1",cyberblade.getWeaponTree().getLastAction().getData().getType());
        model.selectTargets(player1.getPlayerColor(),new ArrayList<>(),1);
        cyberblade.getWeaponTree().updateLastAction(1);
        assertEquals("optional1",cyberblade.getWeaponTree().getLastAction().getData().getType());
        cyberblade.getWeaponTree().updateLastActionPerformed();
        cyberblade.getWeaponTree().updateLastAction(0);
        assertEquals("base",cyberblade.getWeaponTree().getLastAction().getData().getType());
        cyberblade.getWeaponTree().updateLastActionPerformed();
        cyberblade.getWeaponTree().updateLastAction(0);
        assertEquals("optional2",cyberblade.getWeaponTree().getLastAction().getData().getType());
        model.getCurrent().setSelectedBaseTargets(players);
        model.selectTargets(player1.getPlayerColor(),new ArrayList<>(),1);
    }

    @Test
    public void testVoids(){
        model.showWeaponCards(player1.getPlayerColor());
        model.notifyNewton(player1.getPlayerColor(),player2);
        model.chooseNewtonSquare(player1.getPlayerColor(),player2);
        model.useNewton(player1.getPlayerColor());
        model.chooseAction(player1.getPlayerColor());
        model.notifyTeleporter(player1.getPlayerColor());
        model.useTeleporter(player1.getPlayerColor());
        model.choosePowerUp(player1.getPlayerColor());
        model.updateRun();
        model.printMessage(player1.getPlayerColor(),"message1","message2");
    }

    /**
     * tests that the player is spawned on the right square
     */
    @Test
    public void testSpawnPlayer(){
        model.spawnPlayer(player1,AmmoColor.RED);
        assertSame(player1.getPosition(), squares[1][0]);
        model.spawnPlayer(player1,AmmoColor.BLUE);
        assertSame(player1.getPosition(), squares[0][2]);
        model.spawnPlayer(player1,AmmoColor.YELLOW);
        assertSame(player1.getPosition(), squares[2][3]);

    }

    /**
     * tests that the powerUp cards are discarded correctly from the player's hand
     */
    @Test
    public void testDiscardPowerUp(){
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        Teleporter teleporter = new Teleporter(model,AmmoColor.BLUE);
        powerUps.add(teleporter);
        player1.getResources().addPowerUp(powerUps);
        model.discardPowerUp(player1,0);
        assertTrue(model.getGameBoard().getDecks().getDiscardedPowerUpDeck().contains(teleporter));
        assertTrue(!player1.getResources().getPowerUp().contains(teleporter));
        player1.getResources().addPowerUp(powerUps);
        model.requestPowerUpDiscard(player1);
        powerUps.add(teleporter);
        powerUps.add(teleporter);
        player1.getResources().addPowerUp(powerUps);
        model.requestPowerUpDiscard(player1);
    }

}