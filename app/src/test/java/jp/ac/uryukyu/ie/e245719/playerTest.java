package jp.ac.uryukyu.ie.e245719;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class PlayerTest {
    private static World world;
    private static long dummyWindow;

    @BeforeAll
    public static void setup() {
        // GLFWを初期化
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました");
        }
        
        // テスト用の非表示ウィンドウを作成
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        dummyWindow = GLFW.glfwCreateWindow(800, 600, "Test Window", 0, 0);
        
        if (dummyWindow == 0) {
            throw new RuntimeException("ウィンドウの作成に失敗しました");
        }


        // OpenGLコンテキストを作成
        GLFW.glfwMakeContextCurrent(dummyWindow);
        GL.createCapabilities();
        // テスト用のWorldを作成
        world = new World();
    }

    @Test
    public void 初期化できるか() {
        Player testObject = new Player(dummyWindow, world, 0, 2, 0);
        
        Assertions.assertThat(testObject)
            .satisfies(player -> {
                Assertions.assertThat(player.getName()).isEqualTo("player");
                Assertions.assertThat(player.getId()).isEqualTo("Player");
                Assertions.assertThat(player.getX()).isEqualTo(0.0f);
                Assertions.assertThat(player.getY()).isEqualTo(2.0f);
                Assertions.assertThat(player.getZ()).isEqualTo(0.0f);
            });
    }

    @Test
    public void 衝突テスト() {
        Player testPlayer = new Player(dummyWindow, world, 0, 2, 0);
        Block testBlock = new Block("testblock", "stone", 0, 2, 0, 1, 1, 1);
        Collider collider = testBlock.getCollider();
        
        Assertions.assertThat(collider)
            .satisfies(c -> {
                Assertions.assertThat(c.getX()).isEqualTo(0);
                Assertions.assertThat(c.getY()).isEqualTo(2);
                Assertions.assertThat(c.getZ()).isEqualTo(0);
                Assertions.assertThat(c.getWidth()).isEqualTo(1);
                Assertions.assertThat(c.getHeight()).isEqualTo(1);
                Assertions.assertThat(c.getDepth()).isEqualTo(1);
            });

        Assertions.assertThat(testPlayer.checkCollisionInfo(testBlock)).isEqualTo("Collision between player and testblock: YES");
    }
}
