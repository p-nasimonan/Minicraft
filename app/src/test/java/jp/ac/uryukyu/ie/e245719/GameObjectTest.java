package jp.ac.uryukyu.ie.e245719;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestGameObject extends GameObject {
    /**
     * テスト用のGameObjectを初期化します。
     * @param name オブジェクトの名前
     * @param type オブジェクトのタイプ
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @param width 幅
     * @param height 高さ
     * @param depth 奥行き
     */
    public TestGameObject(String name, String type, float x, float y, float z, float width, float height, float depth) {
        super(name, type, x, y, z, width, height, depth);
    }

    @Override
    public void update() {
        // テスト用の実装
    }

    @Override
    public void render() {
        // テスト用の実装
    }
}

public class GameObjectTest {

    @Test
    public void 初期化テスト() {
        TestGameObject testObject = new TestGameObject("test", "test", 0, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(testObject)
            .satisfies(obj -> {
                Assertions.assertThat(obj.getName()).isEqualTo("test");
                Assertions.assertThat(obj.id).isEqualTo("test");
                Assertions.assertThat(obj.x).isEqualTo(0.0f);
                Assertions.assertThat(obj.y).isEqualTo(0.0f);
                Assertions.assertThat(obj.z).isEqualTo(0.0f);
                Assertions.assertThat(obj.width).isEqualTo(1.0f);
                Assertions.assertThat(obj.height).isEqualTo(1.0f);
                Assertions.assertThat(obj.depth).isEqualTo(1.0f);
            });
    }

    @Test
    public void コライダー取得テスト() {
        TestGameObject testObject = new TestGameObject("test", "test", 0, 0, 0, 1, 1, 1);
        Collider collider = testObject.getCollider();
        
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
        TestGameObject obj1 = new TestGameObject("obj1", "test", 0, 0, 0, 1, 1, 1);
        TestGameObject obj2 = new TestGameObject("obj2", "test", 0.5f, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(obj1.getCollider().intersects(obj2.getCollider()))
            .as("重なっているオブジェクトは衝突を検出するべきです")
            .isTrue();
        
        TestGameObject obj3 = new TestGameObject("obj3", "test", 2, 0, 0, 1, 1, 1);
        
        Assertions.assertThat(obj1.getCollider().intersects(obj3.getCollider()))
            .as("重なっていないオブジェクトは衝突を検出しないべきです")
            .isFalse();
    }

    @Test
    public void デバッグ情報テスト() {
        TestGameObject testObject = new TestGameObject("test", "test", 1, 2, 3, 1, 1, 1);
        String collisionInfo = testObject.checkCollisionInfo(
            new TestGameObject("other", "test", 5, 5, 5, 1, 1, 1)
        );
        
        Assertions.assertThat(collisionInfo)
            .as("衝突情報にはオブジェクト名と衝突状態が含まれるべきです")
            .contains("test")
            .contains("other")
            .contains("NO");
    }

    @Test
    public void 衝突判定が正しく機能する() {
        TestGameObject obj1 = new TestGameObject("obj1", "test", 0, 0, 0, 1, 1, 1);
        TestGameObject obj2 = new TestGameObject("obj2", "test", 0.5f, 0.5f, 0.5f, 1, 1, 1);
        Assertions.assertThat(obj1.getCollider().intersects(obj2.getCollider()))
            .as("重なっているオブジェクトは衝突を検出するべきです")
            .isTrue();
    }

    @Test
    public void 間違った名前に変更した場合正常に例外が起こる() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TestGameObject obj1 = new TestGameObject("testBlock", "stone", 0, 0, 0, 1, 1, 1);
            obj1.setName("a");
        });
        assertNotNull(exception);
        System.out.println("Actual exception message: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("名前は1文字以上16時未満で設定してください"));
    }
}
