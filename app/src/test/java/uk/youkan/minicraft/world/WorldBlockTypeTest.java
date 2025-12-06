package uk.youkan.minicraft.world;

import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.world.block.BlockType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * World の BlockType 関連メソッドのテスト
 */
public class WorldBlockTypeTest {

    @Test
    public void ワールド座標でブロックタイプを設定取得できる() {
        System.out.println("=== ワールド座標 BlockType テスト開始 ===");
        
        World world = new World(32, 16, 32);
        
        // ワールド座標でブロック配置
        world.setBlockTypeAt(5, 5, 5, BlockType.STONE);
        BlockType retrieved = world.getBlockTypeAt(5, 5, 5);
        
        System.out.println("配置したブロック: " + BlockType.STONE.getName());
        System.out.println("取得したブロック: " + retrieved.getName());
        
        assertEquals(BlockType.STONE, retrieved);
        assertEquals("stone", retrieved.getStringId());
        
        System.out.println("=== ワールド座標 BlockType テスト完了 ===");
    }

    @Test
    public void 負の座標でもブロックタイプを設定取得できる() {
        World world = new World(32, 16, 32);
        
        // 負の座標（originX は -width/2 なので範囲内）
        world.setBlockTypeAt(-10, 5, -10, BlockType.GRASS);
        BlockType retrieved = world.getBlockTypeAt(-10, 5, -10);
        
        assertEquals(BlockType.GRASS, retrieved);
    }

    @Test
    public void チャンク境界を跨いでブロック設定できる() {
        World world = new World(32, 16, 32);
        
        System.out.println("=== チャンク境界テスト ===");
        System.out.println("ワールド origin: (" + world.getOriginX() + ", " + world.getOriginY() + ", " + world.getOriginZ() + ")");
        System.out.println("ワールド範囲: X[" + world.getOriginX() + " to " + (world.getOriginX() + world.getWidth() - 1) + "]");
        
        // チャンク0とチャンク1の境界
        // origin=-16 なので、チャンク0は -16～-1、チャンク1は 0～15
        int testX1 = -1, testY1 = 5, testZ1 = -1; // チャンク0の端
        world.setBlockTypeAt(testX1, testY1, testZ1, BlockType.STONE);
        BlockType result1 = world.getBlockTypeAt(testX1, testY1, testZ1);
        System.out.println("座標 (" + testX1 + ", " + testY1 + ", " + testZ1 + "): " + result1);
        
        int testX2 = 0, testY2 = 5, testZ2 = 0; // チャンク1の始まり
        world.setBlockTypeAt(testX2, testY2, testZ2, BlockType.GRASS);
        BlockType result2 = world.getBlockTypeAt(testX2, testY2, testZ2);
        System.out.println("座標 (" + testX2 + ", " + testY2 + ", " + testZ2 + "): " + result2);
        
        assertEquals(BlockType.STONE, result1);
        assertEquals(BlockType.GRASS, result2);
    }

    @Test
    public void ワールドの初期状態を確認() {
        World world = new World(32, 16, 32);
        
        // Y=0 は草ブロック
        BlockType groundBlock = world.getBlockTypeAt(0, 0, 0);
        assertEquals(BlockType.GRASS, groundBlock);
        
        // Y=1 以上は空気ブロック
        BlockType airBlock = world.getBlockTypeAt(0, 1, 0);
        assertEquals(BlockType.AIR, airBlock);
    }

    @Test
    public void 複数のブロックタイプを連続配置() {
        World world = new World(32, 16, 32);
        
        // 3種類のブロックを配置
        world.setBlockTypeAt(0, 5, 0, BlockType.GRASS);
        world.setBlockTypeAt(1, 5, 0, BlockType.STONE);
        world.setBlockTypeAt(2, 5, 0, BlockType.DIRT);
        
        assertEquals(BlockType.GRASS, world.getBlockTypeAt(0, 5, 0));
        assertEquals(BlockType.STONE, world.getBlockTypeAt(1, 5, 0));
        assertEquals(BlockType.DIRT, world.getBlockTypeAt(2, 5, 0));
    }
}
