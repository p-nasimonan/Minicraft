package uk.youkan.minicraft.world;

import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.world.block.Block;
import uk.youkan.minicraft.world.block.BlockType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Chunk と Block の統合テスト
 * BlockType が正しく Block に反映されるかをテスト
 */
public class ChunkBlockIntegrationTest {

    @Test
    public void チャンクから生成されたBlockの色が正しい() {
        System.out.println("=== Block 色テスト開始 ===");
        
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);
        
        // 草ブロックを配置
        chunk.setBlock(5, 5, 5, BlockType.GRASS);
        Block grassBlock = chunk.getBlockObject(5, 5, 5);
        assertNotNull(grassBlock);
        
        float[] grassColor = grassBlock.getRenderer().getColor();
        System.out.println("草ブロックの色: R=" + grassColor[0] + " G=" + grassColor[1] + " B=" + grassColor[2]);
        assertEquals(0.0f, grassColor[0], 0.01f);
        assertEquals(0.5f, grassColor[1], 0.01f);
        assertEquals(0.0f, grassColor[2], 0.01f);
        
        // 石ブロックを配置
        chunk.setBlock(6, 6, 6, BlockType.STONE);
        Block stoneBlock = chunk.getBlockObject(6, 6, 6);
        assertNotNull(stoneBlock);
        
        float[] stoneColor = stoneBlock.getRenderer().getColor();
        System.out.println("石ブロックの色: R=" + stoneColor[0] + " G=" + stoneColor[1] + " B=" + stoneColor[2]);
        assertEquals(0.3f, stoneColor[0], 0.01f);
        assertEquals(0.3f, stoneColor[1], 0.01f);
        assertEquals(0.3f, stoneColor[2], 0.01f);
        
        System.out.println("=== Block 色テスト完了 ===");
    }

    @Test
    public void チャンクから生成されたBlockのIDが正しい() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);
        
        // 各ブロックタイプを配置
        chunk.setBlock(0, 0, 0, BlockType.GRASS);
        chunk.setBlock(1, 1, 1, BlockType.STONE);
        chunk.setBlock(2, 2, 2, BlockType.DIRT);
        
        Block grassBlock = chunk.getBlockObject(0, 0, 0);
        Block stoneBlock = chunk.getBlockObject(1, 1, 1);
        Block dirtBlock = chunk.getBlockObject(2, 2, 2);
        
        assertEquals("grass", grassBlock.getId());
        assertEquals("stone", stoneBlock.getId());
        assertEquals("dirt", dirtBlock.getId());
    }

    @Test
    public void ブロックキャッシュが正しく機能する() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);
        
        chunk.setBlock(5, 5, 5, BlockType.GRASS);
        
        // 1回目の取得
        Block block1 = chunk.getBlockObject(5, 5, 5);
        // 2回目の取得（キャッシュから）
        Block block2 = chunk.getBlockObject(5, 5, 5);
        
        // 同じインスタンスが返される
        assertSame(block1, block2);
    }

    @Test
    public void ブロック変更でキャッシュが無効化される() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);
        
        chunk.setBlock(5, 5, 5, BlockType.GRASS);
        Block grassBlock = chunk.getBlockObject(5, 5, 5);
        assertEquals("grass", grassBlock.getId());
        
        // ブロックを変更
        chunk.setBlock(5, 5, 5, BlockType.STONE);
        Block stoneBlock = chunk.getBlockObject(5, 5, 5);
        
        // 新しいインスタンスが生成される
        assertNotSame(grassBlock, stoneBlock);
        assertEquals("stone", stoneBlock.getId());
        
        // 色も変わっている
        float[] stoneColor = stoneBlock.getRenderer().getColor();
        assertEquals(0.3f, stoneColor[0], 0.01f);
    }

    @Test
    public void 空気ブロックは描画されない() {
        World world = new World(16, 16, 16);
        Chunk chunk = new Chunk(world, 0, 0, 0);
        
        // デフォルトは空気
        BlockType type = chunk.getBlock(0, 0, 0);
        assertEquals(BlockType.AIR, type);
        assertTrue(type.isAir());
        
        // 空気ブロックの色は透明（黒）
        float[] airColor = BlockType.AIR.getColor();
        assertEquals(0.0f, airColor[0]);
        assertEquals(0.0f, airColor[1]);
        assertEquals(0.0f, airColor[2]);
    }
}
