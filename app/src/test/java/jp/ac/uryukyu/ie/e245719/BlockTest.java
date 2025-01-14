package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BlockTest extends TestBase {

    @Test
    void ブロックが正しく初期化される() {
        Block block = new Block("testBlock", "stone", 0, 0, 0, 1, 1, 1);
        assertEquals("testBlock", block.getName());
        assertEquals("stone", block.id);
        assertEquals(0, block.x);
        assertEquals(0, block.x);
        assertEquals(0, block.z);
    }

    @Test
    void 描画処理が例外を発生させない() {
        Block block = new Block("testBlock", "stone", 0, 0, 0, 1, 1, 1);
        assertDoesNotThrow(block::render);
    }
}