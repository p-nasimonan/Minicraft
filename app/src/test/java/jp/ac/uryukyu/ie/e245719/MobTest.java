package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class TestMob extends Mob {
    public TestMob(String name, String id, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(name, id, attack, x, y, z, hp, width, height, depth);
    }

    @Override
    public void update() {}

    @Override
    public void render() {}
}

public class MobTest extends TestBase {

    @Test
    public void モブが正しく初期化される() {
        TestMob mob = new TestMob("testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        assertEquals("testMob", mob.getName());
        assertEquals(100, mob.getHp());
    }

    @Test
    public void ジャンプ処理が正しく機能する() {
        TestMob mob = new TestMob("testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        mob.onGround = true;
        mob.action.jump();
        assertNotEquals(0, mob.vy);
    }
}