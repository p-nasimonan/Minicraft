package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class WorldTest extends TestBase {

    @Test
    void ワールドが正しく初期化される() {
        System.out.println("=== ワールド初期化テスト開始 ===");
        System.out.println("ブロック配列の確認:");
        System.out.println("  ブロック配列: " + (world.getBlocks() != null ? "初期化済み" : "未初期化"));
        assertNotNull(world.getBlocks());
        System.out.println("Mob配列の確認:");
        System.out.println("  Mob配列: " + (world.getMobs() != null ? "初期化済み" : "未初期化"));
        assertNotNull(world.getMobs());
        System.out.println("=== ワールド初期化テスト完了 ===\n");
    }

    @Test
    void ブロックを置き換えられる() {
        Block block = new Block(world, "testBlock", "stone", 0, 0, 0, 1, 1, 1);
        world.replaceBlock(block);
        assertTrue(world.getBlockAt(0, 0, 0).id.equals("stone"));
    }
}