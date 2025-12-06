package uk.youkan.minicraft.world.block;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BlockType のテスト
 */
public class BlockTypeTest {

    @Test
    public void ブロックタイプが正しく初期化される() {
        // === ブロックタイプ初期化テスト開始 ===
        System.out.println("=== ブロックタイプ初期化テスト開始 ===");

        BlockType grass = BlockType.GRASS;
        assertEquals(1, grass.getId());
        assertEquals("grass", grass.getStringId());
        assertEquals("草ブロック", grass.getName());
        assertFalse(grass.isAir());

        BlockType air = BlockType.AIR;
        assertTrue(air.isAir());

        System.out.println("=== ブロックタイプ初期化テスト完了 ===");
    }

    @Test
    public void 文字列IDから取得できる() {
        BlockType grass = BlockType.fromStringId("grass");
        assertEquals(BlockType.GRASS, grass);

        BlockType stone = BlockType.fromStringId("stone");
        assertEquals(BlockType.STONE, stone);

        BlockType unknown = BlockType.fromStringId("unknown");
        assertEquals(BlockType.AIR, unknown); // デフォルトは空気
    }

    @Test
    public void 数値IDから取得できる() {
        BlockType grass = BlockType.fromId(1);
        assertEquals(BlockType.GRASS, grass);

        BlockType stone = BlockType.fromId(2);
        assertEquals(BlockType.STONE, stone);

        BlockType unknown = BlockType.fromId(999);
        assertEquals(BlockType.AIR, unknown); // デフォルトは空気
    }

    @Test
    public void 色が正しく取得される() {
        float[] grassColor = BlockType.GRASS.getColor();
        assertEquals(0.0f, grassColor[0]);
        assertEquals(0.5f, grassColor[1]);
        assertEquals(0.0f, grassColor[2]);
    }
}
