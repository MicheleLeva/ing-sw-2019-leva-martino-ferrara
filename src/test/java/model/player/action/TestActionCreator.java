package model.player.action;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestActionCreator {

    @Test
    public void testCreateAction()
    {
        assertTrue(ActionCreator.createAction('S') instanceof Shoot);
    }
}
