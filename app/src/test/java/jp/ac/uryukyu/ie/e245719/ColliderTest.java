package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ColliderTest {

    @Test
    void コライダーが正しく初期化される() {
        Collider collider = new Collider(0, 0, 0, 1, 1, 1);
        assertEquals(1, collider.width);
        assertEquals(1, collider.height);
        assertEquals(1, collider.depth);
    }

    @Test
    void 衝突判定が正しく機能する() {
        Collider collider1 = new Collider(0, 0, 0, 1, 1, 1);
        Collider collider2 = new Collider(0.5f, 0.5f, 0.5f, 1, 1, 1);
        assertTrue(collider1.intersects(collider2));
    }
}