package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest extends TestBase {

    @Test
    void アイテムが正しく初期化される() {
        Item item = new Item("testItem", "item1", 0, 0, 0, 1, 1, 1);
        assertEquals("testItem", item.getName());
        assertEquals("item1", item.getId());
    }

    @Test
    void アイテムの描画が例外を発生させない() {
        Item item = new Item("testItem", "item1", 0, 0, 0, 1, 1, 1);
        assertDoesNotThrow(item::render);
    }
}