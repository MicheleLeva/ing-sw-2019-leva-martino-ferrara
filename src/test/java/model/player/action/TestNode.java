package model.player.action;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestNode {
    /**
     * Tests node properties
     */
    @Test
    public void testNode(){
        Node<String> node = new Node<>("string");
        assertNotNull(node.getData());
        assertNull(node.getParent());
        assertTrue(node.getChildren().isEmpty());
        assertEquals("string",node.getData());
        Node<String> child = new Node<>("string2");
        node.addChild(child);
        assertFalse(node.getChildren().isEmpty());
        assertNotNull(child.getParent());
        assertEquals(child,node.getChildren().get(0));


    }
}
