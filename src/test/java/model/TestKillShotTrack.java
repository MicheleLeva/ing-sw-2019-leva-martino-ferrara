package model;

import model.KillShotCell;
import model.KillShotTrack;
import model.player_package.PlayerColor;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
public class TestKillShotTrack {

    private KillShotTrack killShotTrackTest;
    @Before
    public void initKillShotTrack(){
        killShotTrackTest = new KillShotTrack(8);

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
        killShotTrackTest.removeSkull(PlayerColor.YELLOW, 2);
        assertFalse(KSTrack[0].isSkull());
        assertEquals(PlayerColor.YELLOW, KSTrack[0].getTokenColor());
        assertEquals(2, KSTrack[0].getTokenNumber());

    }

}