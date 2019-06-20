package model.player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMarkCounter {

    private MarkCounter markCounterTest;

    @Before
    public void initMarkCounterTest() {
        markCounterTest = new MarkCounter();
    }

    @Test
    public void testMarkCounter(){
        markCounterTest.addMarks(PlayerColor.YELLOW, 2);
        markCounterTest.addMarks(PlayerColor.GREEN, 1);
        assertEquals(2,(int) markCounterTest.getMarkCounter().get(PlayerColor.YELLOW));
        assertEquals(1,(int) markCounterTest.getMarkCounter().get(PlayerColor.GREEN));
        markCounterTest.clearMarks(PlayerColor.YELLOW);
        markCounterTest.addMarks(PlayerColor.GREEN, 1);
        assertEquals(0,(int) markCounterTest.getMarkCounter().get(PlayerColor.YELLOW));
        assertEquals(2,(int) markCounterTest.getMarkCounter().get(PlayerColor.GREEN));
        System.out.println(markCounterTest.printMarkCounter());

    }
}