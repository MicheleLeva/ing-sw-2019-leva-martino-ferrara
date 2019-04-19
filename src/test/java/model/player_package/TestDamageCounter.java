package model.player_package;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDamageCounter {

    private DamageCounter damageCounterTest;

    @Before
    public void initDamageCounterTest(){
        damageCounterTest = new DamageCounter();
    }

    @Test
    public void testDamageCounter(){
        damageCounterTest.addDamage(PlayerColor.YELLOW);
        damageCounterTest.addDamage(PlayerColor.GREEN);
        assertEquals(PlayerColor.YELLOW,damageCounterTest.getDamageCounter().get(0));
        assertEquals(PlayerColor.GREEN,damageCounterTest.getDamageCounter().get(1));
        damageCounterTest.clearDamage();
        assertTrue(damageCounterTest.getDamageCounter().isEmpty());
    }

}