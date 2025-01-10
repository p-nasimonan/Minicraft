package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest extends TestBase {

    @Test
    void ワールドが正しく初期化される() {
        World world = new World();
        assertNotNull(world.getBlocks());
        assertNotNull(world.getGameObjects());
    }

    @Test
    void ブロックを追加できる() {
        World world = new World();
        Block block = new Block("testBlock", "stone", 0, 0, 0, 1, 1, 1);
        world.addBlock(block);
        assertTrue(world.getBlocks().contains(block));
    }
}