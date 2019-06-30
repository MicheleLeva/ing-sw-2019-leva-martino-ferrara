package model.player.action;
import model.game.Ammo;
import model.game.Model;
import model.adrenaline_exceptions.NoReloadedWeaponsExceptions;
import model.cards.weapons.Cyberblade;
import model.cards.weapons.Weapon;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestShoot {
    private Player playerTest;
    private Model modelTest;
    private Action shoot;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        shoot = new Shoot();
    }
    /**
     * Tests that the perform method in Shoot class executes without interruption and modifies the
     * classes it interacts with correctly
     */
    @Test
    public void perform(){
        playerTest.getResources().getAllWeapon().clear();
        try{
            shoot.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof NoReloadedWeaponsExceptions);
        }

        Weapon weapon = new Cyberblade("Weapon",new Ammo(3,3,3),new Ammo(3,3,3),null,null,
                0,0,0,0,0,0,0,0,
                0,null);

        playerTest.getResources().getAllWeapon().add(weapon);
        try{
            shoot.perform(modelTest,playerTest.getPlayerColor());
            assertFalse(playerTest.getResources().getAllWeapon().isEmpty());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
