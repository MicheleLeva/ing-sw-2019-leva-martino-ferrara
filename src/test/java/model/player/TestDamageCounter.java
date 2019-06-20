package model.player;

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
        int damage1 = 4;
        int damage2 = 6;
        damageCounterTest.addDamage(PlayerColor.YELLOW, damage1);
        damageCounterTest.addDamage(PlayerColor.GREEN, damage2);
        assertEquals(PlayerColor.YELLOW,damageCounterTest.getDamageCounter().get(0));
        assertEquals(PlayerColor.YELLOW,damageCounterTest.getDamageCounter().get(3));
        assertEquals(PlayerColor.GREEN,damageCounterTest.getDamageCounter().get(4));
        damageCounterTest.clearDamage();
        assertTrue(damageCounterTest.getDamageCounter().isEmpty());

        damageCounterTest.addDamage(PlayerColor.YELLOW, damage1);
        damageCounterTest.addDamage(PlayerColor.GREEN, damage2);
        System.out.println(damageCounterTest.printDamageCounter());
    }

}