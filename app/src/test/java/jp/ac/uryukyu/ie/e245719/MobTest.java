package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class TestMob extends Mob {
    public TestMob(World world, String name, String id, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(world, name, id, attack, x, y, z, hp, width, height, depth);
    }

    @Override
    public void update() {}

    @Override
    public void render() {}
}

public class MobTest extends TestBase {

    @Test
    public void モブが正しく初期化される() {
        System.out.println("=== モブ初期化テスト開始 ===");
        TestMob mob = new TestMob(world, "testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        System.out.println("作成されたモブ:");
        System.out.println("  名前: " + mob.getName());
        System.out.println("  HP: " + mob.getHp());
        assertEquals("testMob", mob.getName());
        assertEquals(100, mob.getHp());
        System.out.println("=== モブ初期化テスト完了 ===\n");
    }

    @Test
    public void ジャンプ処理が正しく機能する() {
        System.out.println("=== ジャンプ処理テスト開始 ===");
        TestMob mob = new TestMob(world, "testMob", "mob1", 10, 0, 0, 0, 100, 1, 1, 1);
        System.out.println("ジャンプ前の状態:");
        System.out.println("  接地状態: " + mob.onGround);
        System.out.println("  垂直速度: " + mob.vy);
        
        mob.onGround = true;
        mob.action.jump();
        
        System.out.println("ジャンプ後の状態:");
        System.out.println("  接地状態: " + mob.onGround);
        System.out.println("  垂直速度: " + mob.vy);
        assertNotEquals(0, mob.vy);
        System.out.println("=== ジャンプ処理テスト完了 ===\n");
    }
}