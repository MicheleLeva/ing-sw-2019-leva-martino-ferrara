package model.player.action;
import model.Ammo;
import model.Current;
import model.Model;
import model.adrenaline_exceptions.CannotPayException;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.MaxAmmoException;
import model.cards.AmmoCard;
import model.cards.weapons.Cyberblade;
import model.map.Map;
import model.map.Square;
import model.map.SquareColor;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestGrab {
    private Player playerTest;
    private Model modelTest;
    private Action grabAction;
    private Map map;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        modelTest.setGameBoard(1);
        map = new Map(1);
        grabAction = new Grab();
    }

    /**
     * Tests that the perform method in Grab class executes without interruption and modifies the
     * classes it interacts with correctly
     */
    @Test
    public void testPerform(){
        Square square = map.getSquareFromCoordinates(0,0);
        playerTest.setPosition(square);

        square.setAmmo(new AmmoCard(new Ammo(1,1,1),true));
        int currentPowerUpNumber = playerTest.getResources().getPowerUp().size();
        playerTest.getResources().addToAvailableAmmo(2,2,2);

        try{
            grabAction.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof MaxAmmoException);
            assertEquals(playerTest.getResources().getPowerUp().size(), currentPowerUpNumber + 1);
        }

        try{
            grabAction.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof EmptySquareException);
        }

        square.setAmmo(new AmmoCard(new Ammo(1,1,1),false));
        playerTest.getResources().removeFromAvailableAmmo(3,3,3);
        try{
            grabAction.perform(modelTest,playerTest.getPlayerColor());
            Ammo availableAmmo = playerTest.getResources().getAvailableAmmo();
            assertTrue(availableAmmo.getRed() == 1 &&
                                    availableAmmo.getBlue() == 1 &&
                                        availableAmmo.getYellow() == 1);
        }
        catch(Exception e){

        }

        square = map.getSpawnSquare(SquareColor.YELLOW);
        square.setWeapon(new Cyberblade("Weapon",new Ammo(3,3,3),null,null,null,
                    0,0,0,0,0,0,0,0,
                            0,null));
        playerTest.getResources().removeFromAvailableAmmo(1,1,1);
        playerTest.getResources().getPowerUp().clear();
        playerTest.setPosition(square);

        try{
            grabAction.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof CannotPayException);
        }




        square = map.getSpawnSquare(SquareColor.YELLOW);
        square.setWeapon(new Cyberblade("Weapon",new Ammo(0,0,0),null,null,null,
                0,0,0,0,0,0,0,0,
                0,null));
        playerTest.getResources().addToAvailableAmmo(1,1,1);
        playerTest.setPosition(square);
        Current current = modelTest.getCurrent();

        try{
            grabAction.perform(modelTest,playerTest.getPlayerColor());
            assertFalse(current.getPickUpableWeapon().isEmpty());
            assertTrue(current.getPickUpableWeapon().get(0) instanceof Cyberblade);
        }
        catch(Exception e){

        }


    }



}
