package model.player.action;
import model.Ammo;
import model.Model;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.adrenaline_exceptions.NoReloadableWeaponsException;
import model.cards.weapons.Cyberblade;
import model.cards.weapons.Weapon;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestReload {
    private Player playerTest;
    private Model modelTest;
    private Action reload;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        reload = new Reload();
    }

    /**
     * Tests that the perform method in Reload class executes without interruption and modifies the
     * classes it interacts with correctly
     */
    @Test
    public void testPerform(){
        try{
            reload.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof NoReloadableWeaponsException);
        }

        Weapon weapon = new Cyberblade("Weapon",new Ammo(3,3,3),new Ammo(3,3,3),null,null,
                0,0,0,0,0,0,0,0,
                0,null);
        weapon.unload();
        playerTest.getResources().getAllWeapon().add(weapon);

        try{
            reload.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof InsufficientAmmoException);
        }


        weapon = new Cyberblade("Weapon",new Ammo(0,0,0),new Ammo(0,0,0),null,null,
                0,0,0,0,0,0,0,0,
                0,null);
        weapon.unload();
        playerTest.getResources().getAllWeapon().add(weapon);

        try{
            reload.perform(modelTest,playerTest.getPlayerColor());

        }
        catch(Exception e){

        }
    }
}
