package model.player.action;
import model.Model;
import model.adrenaline_exceptions.NoPowerUpException;
import model.cards.AmmoColor;
import model.cards.powerups.PowerUp;
import model.cards.powerups.Teleporter;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestUsePowerUp {
    private Player playerTest;
    private Model modelTest;
    private Action usePowerUp;
    @Before
    public void init(){
        playerTest = new Player("player1", PlayerColor.PURPLE);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        modelTest = new Model(playerList,8);
        usePowerUp = new UsePowerUp();
    }

    /**
     * Tests that the perform method in UsePowerUp class executes without interruption and modifies the
     * classes it interacts with correctly
     */
    @Test
    public void testPerform(){
        playerTest.getResources().getPowerUp().clear();
        try{
            usePowerUp.perform(modelTest,playerTest.getPlayerColor());
        }
        catch(Exception e){
            assertTrue(e instanceof NoPowerUpException);
        }

        PowerUp powerUp = new Teleporter(modelTest, AmmoColor.YELLOW);
        playerTest.getResources().getPowerUp().add(powerUp);

        try{
            usePowerUp.perform(modelTest,playerTest.getPlayerColor());
            assertTrue(!playerTest.getResources().getPowerUp().isEmpty());
        }
        catch(Exception e){

        }
    }
}
