package model;

import model.KillShotCell;
import model.player_package.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestKillShotCell {

    private KillShotCell emptyKillShotCell;
    private KillShotCell fullKillShotCell;



    @Before
    public void initKillShotCell(){
        emptyKillShotCell = new KillShotCell();
        fullKillShotCell  = new KillShotCell(PlayerColor.YELLOW, 1);

    }

    @Test
    public void testKillShotCell(){
    assertTrue(emptyKillShotCell.isSkull());
    assertNull(emptyKillShotCell.getTokenColor());
    assertEquals(0, emptyKillShotCell.getTokenNumber());

    assertFalse(fullKillShotCell.isSkull());
    assertEquals(PlayerColor.YELLOW, fullKillShotCell.getTokenColor());
    assertEquals(1, fullKillShotCell.getTokenNumber());

    }

}
