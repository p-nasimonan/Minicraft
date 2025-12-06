package uk.youkan.minicraft.entity;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.TestBase;
import uk.youkan.minicraft.world.World;

class TestEntity extends AbstractEntity {
    public TestEntity(World world, String name, String type, float x, float y, float z, float width, float height, float depth) {
        super(world, name, type, x, y, z, width, height, depth);
    }

    @Override
    public void update() {}

    @Override
    public void render() {}
}

public class EntityTest extends TestBase {

    @Test
    public void 初期化テスト() {
        TestEntity testObject = new TestEntity(world, "test", "test", 0, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(testObject)
            .satisfies(obj -> {
                Assertions.assertThat(obj.getName()).isEqualTo("test");
                Assertions.assertThat(obj.getId()).isEqualTo("test");
                Assertions.assertThat(obj.getX()).isEqualTo(0.0f);
                Assertions.assertThat(obj.getY()).isEqualTo(0.0f);
                Assertions.assertThat(obj.getZ()).isEqualTo(0.0f);
            });
    }

    @Test
    public void コライダー取得テスト() {
        TestEntity testObject = new TestEntity(world, "test", "test", 0, 0, 0, 1, 1, 1);
        var collider = testObject.getCollider();
        
        Assertions.assertThat(collider)
            .satisfies(c -> {
                Assertions.assertThat(c.x).isEqualTo(0.0f);
                Assertions.assertThat(c.y).isEqualTo(0.0f);
                Assertions.assertThat(c.z).isEqualTo(0.0f);
                Assertions.assertThat(c.width).isEqualTo(1.0f);
                Assertions.assertThat(c.height).isEqualTo(1.0f);
                Assertions.assertThat(c.depth).isEqualTo(1.0f);
            });
    }

    @Test
    public void 衝突検出テスト() {
        TestEntity obj1 = new TestEntity(world, "obj1", "test", 0, 0, 0, 1, 1, 1);
        TestEntity obj2 = new TestEntity(world, "obj2", "test", 0.5f, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(obj1.getCollider().intersects(obj2.getCollider()))
            .as("重なっているオブジェクトは衝突を検出するべきです")
            .isTrue();
        
        TestEntity obj3 = new TestEntity(world, "obj3", "test", 2, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(obj1.getCollider().intersects(obj3.getCollider()))
            .as("重なっていないオブジェクトは衝突を検出しないべきです")
            .isFalse();
    }

    @Test
    public void デバッグ情報テスト() {
        TestEntity testObject = new TestEntity(world, "test", "test", 1, 2, 3, 1, 1, 1);
        String collisionInfo = testObject.checkCollisionInfo(
            new TestEntity(world, "other", "test", 5, 5, 5, 1, 1, 1)
        );
        
        Assertions.assertThat(collisionInfo)
            .as("衝突情報にはオブジェクト名と衝突状態が含まれるべきです")
            .contains("test")
            .contains("other")
            .contains("NO");
    }

    @Test
    public void 衝突判定が正しく機能する() {
        TestEntity obj1 = new TestEntity(world, "obj1", "test", 0, 0, 0, 1, 1, 1);
        TestEntity obj2 = new TestEntity(world, "obj2", "test", 0.5f, 0.5f, 0.5f, 1, 1, 1);
        Assertions.assertThat(obj1.getCollider().intersects(obj2.getCollider()))
            .as("重なっているオブジェクトは衝突を検出するべきです")
            .isTrue();
    }

    @Test
    public void 間違った名前に変更した場合正常に例外が起こる() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TestEntity obj1 = new TestEntity(world, "testBlock", "stone", 0, 0, 0, 1, 1, 1);
            obj1.setName("a");
        });
        assertNotNull(exception);
        System.out.println("Actual exception message: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("名前は1文字以上16文字未満で設定してください"));
    }
}
