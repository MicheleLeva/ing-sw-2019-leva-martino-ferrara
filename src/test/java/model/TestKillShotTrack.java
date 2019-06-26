package model;

import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestKillShotTrack {

    private KillShotTrack killShotTrackTest;
    private Player player1 ;
    private Player player2;
    private Player player3;
    private Model modelTest;
    private ArrayList<Player> players = new ArrayList<>();
    @Before
    public void initKillShotTrack(){
        player1 = new Player("player1",PlayerColor.BLUE);
        player2 = new Player("player2",PlayerColor.GREEN);
        player3 = new Player("player3",PlayerColor.YELLOW);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        modelTest = new Model(players, 8);
        killShotTrackTest = new KillShotTrack(8, modelTest);

    }

    @Test
    public void testKillShotTrackGetter(){

        KillShotCell[] KSTrack = killShotTrackTest.getKillShotTrack();
        System.out.println(KSTrack[0].isSkull());

        for(int i = 0; i < 8; i++){
            assertTrue(KSTrack[i].isSkull());
            assertNull(KSTrack[i].getTokenColor());
            assertEquals(0, KSTrack[i].getTokenNumber());
        }
    }

    @Test
    public void testRemove(){
        KillShotCell[] KSTrack = killShotTrackTest.getKillShotTrack();
        killShotTrackTest.removeSkull(PlayerColor.YELLOW);
        assertFalse(KSTrack[0].isSkull());
        assertEquals(PlayerColor.YELLOW, KSTrack[0].getTokenColor());
        assertEquals(1, KSTrack[0].getTokenNumber());

    }

    @Test
    public void testPrintKillShotTrack(){
        System.out.print(killShotTrackTest.printKillshotTrack());
    }

}
