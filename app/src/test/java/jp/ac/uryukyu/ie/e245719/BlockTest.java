package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest extends TestBase {

    @Test
    void ブロックが正しく初期化される() {
        Block block = new Block("testBlock", "stone", 0, 0, 0, 1, 1, 1);
        assertEquals("testBlock", block.getName());
        assertEquals("stone", block.getId());
        assertEquals(0, block.getX());
        assertEquals(0, block.getY());
        assertEquals(0, block.getZ());
    }

    @Test
    void 描画処理が例外を発生させない() {
        Block block = new Block("testBlock", "stone", 0, 0, 0, 1, 1, 1);
        assertDoesNotThrow(block::render);
    }
}