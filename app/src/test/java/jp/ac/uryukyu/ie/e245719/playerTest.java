package jp.ac.uryukyu.ie.e245719;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class PlayerTest {
    private static World world;
    private static InterFace interFace;
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

        // テスト用のInterFaceを作成
        interFace = new InterFace(dummyWindow);
    }

    @Test
    public void 初期化できるか() {
        Player testObject = new Player(interFace, world, 0, 2, 0);
        
        Assertions.assertThat(testObject)
            .satisfies(player -> {
                Assertions.assertThat(player.getName()).isEqualTo("player");
                Assertions.assertThat(player.id).isEqualTo("Player");
                Assertions.assertThat(player.x).isEqualTo(0.0f);
                Assertions.assertThat(player.y).isEqualTo(2.0f);
                Assertions.assertThat(player.z).isEqualTo(0.0f);
            });
    }

    @Test
    public void 衝突テスト() {
        Player testPlayer = new Player(interFace, world, 0, 2, 0);
        Block testBlock = new Block("testblock", "stone", 0, 2, 0, 1, 1, 1);
        Collider collider = testBlock.getCollider();
        
        Assertions.assertThat(collider)
            .satisfies(c -> {
                Assertions.assertThat(c.x).isEqualTo(0);
                Assertions.assertThat(c.y).isEqualTo(2);
                Assertions.assertThat(c.z).isEqualTo(0);
                Assertions.assertThat(c.width).isEqualTo(1);
                Assertions.assertThat(c.height).isEqualTo(1);
                Assertions.assertThat(c.depth).isEqualTo(1);
            });

        Assertions.assertThat(testPlayer.checkCollisionInfo(testBlock)).isEqualTo("Collision between player and testblock: YES");
    }
}
