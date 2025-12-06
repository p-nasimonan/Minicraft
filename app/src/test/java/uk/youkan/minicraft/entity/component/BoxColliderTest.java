package uk.youkan.minicraft.entity.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class BoxColliderTest {

    @Test
    void コライダーが正しく初期化される() {
        System.out.println("=== コライダー初期化テスト開始 ===");
        Transform transform = new Transform(0, 0, 0, 1, 1, 1);
        BoxCollider collider = new BoxCollider(transform);
        System.out.println("作成されたコライダー:");
        System.out.println("  サイズ: " + transform.getWidth() + " x " + transform.getHeight() + " x " + transform.getDepth());
        assertEquals(1, transform.getWidth());
        System.out.println("=== コライダー初期化テスト完了 ===\n");
    }

    @Test
    void 衝突判定が正しく機能する() {
        System.out.println("=== 衝突判定テスト開始 ===");
        Transform transform1 = new Transform(0, 0, 0, 1, 1, 1);
        Transform transform2 = new Transform(0.5f, 0.5f, 0.5f, 1, 1, 1);
        BoxCollider collider1 = new BoxCollider(transform1);
        BoxCollider collider2 = new BoxCollider(transform2);
        System.out.println("コライダー1の位置: (0, 0, 0)");
        System.out.println("コライダー2の位置: (0.5, 0.5, 0.5)");
        boolean result = collider1.intersects(collider2);
        System.out.println("衝突判定結果: " + (result ? "衝突している" : "衝突していない"));
        assertTrue(result);
        System.out.println("=== 衝突判定テスト完了 ===\n");
    }

    @Test
    void レイヤーによる衝突応答が正しく機能する() {
        System.out.println("=== レイヤー衝突応答テスト開始 ===");
        
        Transform mobTransform = new Transform(0, 0, 0, 1, 1, 1);
        Transform blockTransform = new Transform(0.5f, 0, 0, 1, 1, 1);
        Transform itemTransform = new Transform(0, 0, 0.5f, 0.5f, 0.5f, 0.5f);
        
        BoxCollider mobCollider = new BoxCollider(mobTransform, CollisionLayer.MOB);
        BoxCollider blockCollider = new BoxCollider(blockTransform, CollisionLayer.BLOCK);
        BoxCollider itemCollider = new BoxCollider(itemTransform, CollisionLayer.ITEM);
        
        // MOB vs BLOCK → 移動ブロック
        assertTrue(mobCollider.shouldBlockMovement(blockCollider), "MOBはBLOCKで止まるべき");
        System.out.println("MOB vs BLOCK: 移動ブロック ✓");
        
        // MOB vs ITEM → トリガー（通過可能）
        assertFalse(mobCollider.shouldBlockMovement(itemCollider), "MOBはITEMを通過できるべき");
        assertTrue(mobCollider.shouldTriggerEvent(itemCollider), "MOB vs ITEMでトリガーイベント発生");
        System.out.println("MOB vs ITEM: 通過可能、トリガー発生 ✓");
        
        System.out.println("=== レイヤー衝突応答テスト完了 ===\n");
    }
}
