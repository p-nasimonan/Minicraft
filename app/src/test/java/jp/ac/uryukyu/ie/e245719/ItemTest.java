package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testItemInitialization() {
        Item item = new Item("testItem", "item1", 0, 0, 0, 1, 1, 1);
        assertEquals("testItem", item.getName());
    }

    @Test
    void testItemRender() {
        Item item = new Item("testItem", "item1", 0, 0, 0, 1, 1, 1);
        assertDoesNotThrow(item::render);
    }
}