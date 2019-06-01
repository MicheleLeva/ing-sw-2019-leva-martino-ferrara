package model.player.action;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestActionCreator {
    @Test
    public static void testCreateAction()
    {
        Action action1 = ActionCreator.createAction('S');
        assertEquals(action1,new Shoot());
    }
}
