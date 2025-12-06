package uk.youkan.minicraft.world;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.TestBase;
import uk.youkan.minicraft.world.block.Block;
import uk.youkan.minicraft.world.block.BlockType;

class WorldTest extends TestBase {

    @Test
    void ワールドが正しく初期化される() {
        System.out.println("=== ワールド初期化テスト開始 ===");
        System.out.println("Chunk配列の確認:");
        System.out.println("  Chunk配列: " + (world.getChunks() != null ? "初期化済み" : "未初期化"));
        assertNotNull(world.getChunks());
        System.out.println("Mob配列の確認:");
        System.out.println("  Mob配列: " + (world.getMobs() != null ? "初期化済み" : "未初期化"));
        assertNotNull(world.getMobs());
        System.out.println("=== ワールド初期化テスト完了 ===\n");
    }

    @Test
    void ブロックを置き換えられる() {
        // Chunk システムでブロックを設定
        world.setBlockTypeAt(0, 0, 0, BlockType.STONE);
        
        // Block オブジェクトとして取得
        Block block = world.getBlockAt(0, 0, 0);
        assertEquals("stone", block.getId());
        assertFalse(block.isAir());
        
        // BlockType として取得
        BlockType blockType = world.getBlockTypeAt(0, 0, 0);
        assertEquals(BlockType.STONE, blockType);
    }
}
