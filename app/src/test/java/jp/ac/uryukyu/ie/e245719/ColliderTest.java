package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ColliderTest {

    @Test
    void コライダーが正しく初期化される() {
        System.out.println("=== コライダー初期化テスト開始 ===");
        Collider collider = new Collider(0, 0, 0, 1, 1, 1);
        System.out.println("作成されたコライダー:");
        System.out.println("  サイズ: " + collider.width + " x " + collider.height + " x " + collider.depth);
        assertEquals(1, collider.width);
        System.out.println("=== コライダー初期化テスト完了 ===\n");
    }

    @Test
    void 衝突判定が正しく機能する() {
        System.out.println("=== 衝突判定テスト開始 ===");
        Collider collider1 = new Collider(0, 0, 0, 1, 1, 1);
        Collider collider2 = new Collider(0.5f, 0.5f, 0.5f, 1, 1, 1);
        System.out.println("コライダー1の位置: (0, 0, 0)");
        System.out.println("コライダー2の位置: (0.5, 0.5, 0.5)");
        boolean result = collider1.intersects(collider2);
        System.out.println("衝突判定結果: " + (result ? "衝突している" : "衝突していない"));
        assertTrue(result);
        System.out.println("=== 衝突判定テスト完了 ===\n");
    }
}