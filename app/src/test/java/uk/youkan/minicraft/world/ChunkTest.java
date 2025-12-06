package uk.youkan.minicraft.world;

import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.world.block.BlockType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Chunk のテスト
 */
public class ChunkTest {

    @Test
    public void チャンクが正しく初期化される() {
        System.out.println("=== チャンク初期化テスト開始 ===");

        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);

        assertNotNull(chunk);
        assertEquals(0, chunk.getChunkX());
        assertEquals(0, chunk.getChunkY());
        assertEquals(0, chunk.getChunkZ());

        // デフォルトで空気ブロック
        BlockType blockType = chunk.getBlock(0, 0, 0);
        assertEquals(BlockType.AIR, blockType);

        System.out.println("=== チャンク初期化テスト完了 ===");
    }

    @Test
    public void ブロックを設定取得できる() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);

        // 草ブロックを設定
        chunk.setBlock(5, 5, 5, BlockType.GRASS);
        BlockType retrieved = chunk.getBlock(5, 5, 5);
        assertEquals(BlockType.GRASS, retrieved);

        // 石ブロックを設定
        chunk.setBlock(10, 10, 10, BlockType.STONE);
        retrieved = chunk.getBlock(10, 10, 10);
        assertEquals(BlockType.STONE, retrieved);
    }

    @Test
    public void 範囲外アクセスは安全() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);

        // 範囲外の座標は空気を返す
        BlockType blockType = chunk.getBlock(-1, 0, 0);
        assertEquals(BlockType.AIR, blockType);

        blockType = chunk.getBlock(100, 100, 100);
        assertEquals(BlockType.AIR, blockType);
    }

    @Test
    public void チャンクのダーティフラグが機能する() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);

        assertTrue(chunk.isDirty()); // 初期状態は dirty

        // レンダリング後は clean
        chunk.render();
        assertFalse(chunk.isDirty());

        // ブロック変更で再度 dirty
        chunk.setBlock(0, 0, 0, BlockType.GRASS);
        assertTrue(chunk.isDirty());
    }
}
