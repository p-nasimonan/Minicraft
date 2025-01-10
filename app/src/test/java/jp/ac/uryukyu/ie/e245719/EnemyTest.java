package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemyTest extends TestBase {

    @Test
    void 敵が正しく初期化される() {
        Enemy enemy = new Enemy("enemy1", "Enemy", 10, 0, 0, 0, 100, 1, 1, 1);
        assertEquals("enemy1", enemy.getId());
        assertEquals(100, enemy.getHp());
    }

    @Test
    void 敵の更新処理が正しく機能する() {
        Enemy enemy = new Enemy("enemy1", "Enemy", 10, 0, 0, 0, 100, 1, 1, 1);
        assertDoesNotThrow(enemy::update);
    }
}