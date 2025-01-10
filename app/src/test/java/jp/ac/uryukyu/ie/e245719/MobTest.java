package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMob extends Mob {
    public TestMob(String name, String id, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(name, id, attack, x, y, z, hp, width, height, depth);
    }

    @Override
    public void update() {}

    @Override
    public void render() {}
}

class MobTest {

    @Test
    void testMobInitialization() {
        TestMob mob = new TestMob("testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        assertEquals("testMob", mob.getName());
    }

    @Test
    void testMobJump() {
        TestMob mob = new TestMob("testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        mob.jump();
        assertNotEquals(0, mob.vy);
    }
}