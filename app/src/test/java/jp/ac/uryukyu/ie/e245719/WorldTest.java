package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class WorldTest extends TestBase {

    @Test
    void ワールドが正しく初期化される() {
        assertNotNull(world.getBlocks());
        assertNotNull(world.getMobs());
    }

    @Test
    void ブロックを置き換えられる() {
        Block block = new Block(world, "testBlock", "stone", 0, 0, 0, 1, 1, 1);
        world.replaceBlock(block);
        assertTrue(world.getBlockAt(0, 0, 0).id.equals("stone"));
    }
}