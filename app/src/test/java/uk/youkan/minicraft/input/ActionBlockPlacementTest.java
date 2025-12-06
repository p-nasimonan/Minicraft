package uk.youkan.minicraft.input;

import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.entity.Enemy;
import uk.youkan.minicraft.world.World;
import uk.youkan.minicraft.world.block.BlockType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Action の replaceBlockInDirection のテスト
 * BlockType システムとの整合性を検証
 */
public class ActionBlockPlacementTest {

    @Test
    public void ブロック配置がChunkシステムに反映される() {
        System.out.println("=== ブロック配置テスト開始 ===");
        
        World world = new World(32, 16, 32);
        Enemy testMob = new Enemy(world, "tester", "test_enemy", 10, 0, 5, 0, 100, 1, 4, 1);
        
        // Mob の前方にブロックを配置
        testMob.action.replaceBlockInDirection(0, 0, "stone");
        
        // 配置されたブロックを確認（前方2ブロック先）
        int expectedZ = -2; // 前方（-Z方向）
        BlockType placedBlock = world.getBlockTypeAt(0, 6, expectedZ);
        
        System.out.println("配置したブロックタイプ: " + placedBlock);
        System.out.println("期待されるブロックタイプ: " + BlockType.STONE);
        
        assertEquals(BlockType.STONE, placedBlock);
        assertFalse(placedBlock.isAir());
        
        System.out.println("=== ブロック配置テスト完了 ===");
    }

    @Test
    public void 空気ブロック配置でブロックが消える() {
        World world = new World(32, 16, 32);
        Enemy testMob = new Enemy(world, "tester", "test_enemy", 10, 0, 5, 0, 100, 1, 4, 1);
        
        // まず石ブロックを配置
        testMob.action.replaceBlockInDirection(0, 0, "stone");
        int targetZ = -2;
        assertEquals(BlockType.STONE, world.getBlockTypeAt(0, 6, targetZ));
        
        // 次に空気ブロックで置き換え（削除）
        testMob.action.replaceBlockInDirection(0, 0, "air");
        BlockType afterRemoval = world.getBlockTypeAt(0, 6, targetZ);
        
        assertEquals(BlockType.AIR, afterRemoval);
        assertTrue(afterRemoval.isAir());
    }

    @Test
    public void 異なるブロックタイプを連続配置() {
        World world = new World(32, 16, 32);
        Enemy testMob = new Enemy(world, "tester", "test_enemy", 10, 0, 5, 0, 100, 1, 4, 1);
        
        // 石ブロック配置
        testMob.action.replaceBlockInDirection(0, 0, "stone");
        assertEquals(BlockType.STONE, world.getBlockTypeAt(0, 6, -2));
        
        // 草ブロックで置き換え
        testMob.action.replaceBlockInDirection(0, 0, "grass");
        assertEquals(BlockType.GRASS, world.getBlockTypeAt(0, 6, -2));
        
        // 土ブロックで置き換え
        testMob.action.replaceBlockInDirection(0, 0, "dirt");
        assertEquals(BlockType.DIRT, world.getBlockTypeAt(0, 6, -2));
    }

    @Test
    public void Chunkシステムでブロック配置と取得() {
        World world = new World(32, 16, 32);
        Enemy testMob = new Enemy(world, "tester", "test_enemy", 10, 0, 5, 0, 100, 1, 4, 1);
        
        // ブロック配置
        testMob.action.replaceBlockInDirection(0, 0, "stone");
        
        // Chunk システムで確認
        BlockType chunkBlock = world.getBlockTypeAt(0, 6, -2);
        assertEquals(BlockType.STONE, chunkBlock);
        
        // Block オブジェクトも取得可能
        uk.youkan.minicraft.world.block.Block blockObject = world.getBlockAt(0, 6, -2);
        assertEquals("stone", blockObject.getId());
        assertFalse(blockObject.isAir());
    }

    @Test
    public void 向きを変えてブロック配置() {
        World world = new World(32, 16, 32);
        Enemy testMob = new Enemy(world, "tester", "test_enemy", 10, 0, 5, 0, 100, 1, 4, 1);
        
        // 北向き（yaw=0）で配置
        testMob.action.replaceBlockInDirection(0, 0, "stone");
        assertEquals(BlockType.STONE, world.getBlockTypeAt(0, 6, -2));
        
        // 東向き（yaw=90）で配置
        testMob.action.replaceBlockInDirection(0, 90, "grass");
        // 東方向（+X）に配置される
        BlockType eastBlock = world.getBlockTypeAt(2, 6, 0);
        assertEquals(BlockType.GRASS, eastBlock);
    }
}
