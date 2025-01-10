package jp.ac.uryukyu.ie.e245719;

import org.assertj.core.api.Assertions;
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
                Assertions.assertThat(obj.getId()).isEqualTo("test");
                Assertions.assertThat(obj.getX()).isEqualTo(0.0f);
                Assertions.assertThat(obj.getY()).isEqualTo(0.0f);
                Assertions.assertThat(obj.getZ()).isEqualTo(0.0f);
                Assertions.assertThat(obj.getWidth()).isEqualTo(1.0f);
                Assertions.assertThat(obj.getHeight()).isEqualTo(1.0f);
                Assertions.assertThat(obj.getDepth()).isEqualTo(1.0f);
            });
    }

    @Test
    public void コライダー取得テスト() {
        TestGameObject testObject = new TestGameObject("test", "test", 0, 0, 0, 1, 1, 1);
        Collider collider = testObject.getCollider();
        
        Assertions.assertThat(collider)
            .satisfies(c -> {
                Assertions.assertThat(c.getX()).isEqualTo(0.0f);
                Assertions.assertThat(c.getY()).isEqualTo(0.0f);
                Assertions.assertThat(c.getZ()).isEqualTo(0.0f);
                Assertions.assertThat(c.getWidth()).isEqualTo(1.0f);
                Assertions.assertThat(c.getHeight()).isEqualTo(1.0f);
                Assertions.assertThat(c.getDepth()).isEqualTo(1.0f);
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

}
