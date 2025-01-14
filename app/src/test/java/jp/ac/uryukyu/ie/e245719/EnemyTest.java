package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class EnemyTest extends TestBase {

    @Test
    void 敵が正しく初期化される() {
        Enemy enemy = new Enemy("enemy1", "Enemy", 10, 0, 0, 0, 100, 1, 1, 1);
        assertEquals("Enemy", enemy.id);
        assertEquals(100, enemy.getHp());
    }

    @Test
    void 敵の更新処理が正しく機能する() {
        Enemy enemy = new Enemy("enemy1", "Enemy", 10, 0, 0, 0, 100, 1, 1, 1);
        assertDoesNotThrow(enemy::update);
    }
}