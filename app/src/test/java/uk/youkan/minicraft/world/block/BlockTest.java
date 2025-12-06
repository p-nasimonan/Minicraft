package uk.youkan.minicraft.world.block;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.TestBase;

class BlockTest extends TestBase {

    @Test
    void ブロックが正しく初期化される() {
        System.out.println("=== ブロック初期化テスト開始 ===");
        Block block = new Block(world, "testBlock", "stone", 0, 0, 0, 1, 1, 1);
        System.out.println("作成されたブロック:");
        System.out.println("  名前: " + block.getName());
        System.out.println("  ID: " + block.getId());
        System.out.println("  位置: (" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ")");
        assertEquals("testBlock", block.getName());
        assertEquals("stone", block.getId());
        assertEquals(0, block.getX());
        System.out.println("=== ブロック初期化テスト完了 ===\n");
    }

    @Test
    void 描画処理が例外を発生させない() {
        Block block = new Block(world, "testBlock", "stone", 0, 0, 0, 1, 1, 1);
        assertDoesNotThrow(block::render);
    }
}
