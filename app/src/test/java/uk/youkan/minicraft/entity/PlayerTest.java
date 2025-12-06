package uk.youkan.minicraft.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import uk.youkan.minicraft.input.InputManager;
import uk.youkan.minicraft.world.World;
import uk.youkan.minicraft.world.block.Block;

public class PlayerTest {
    private static World world;
    private static InputManager inputManager;
    private static long dummyWindow;

    @BeforeAll
    public static void setup() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました");
        }
        
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        dummyWindow = GLFW.glfwCreateWindow(800, 600, "Test Window", 0, 0);
        
        if (dummyWindow == 0) {
            throw new RuntimeException("ウィンドウの作成に失敗しました");
        }

        GLFW.glfwMakeContextCurrent(dummyWindow);
        GL.createCapabilities();

        world = new World(10, 10, 10);
        inputManager = new InputManager(dummyWindow);
    }

    @Test
    public void 初期化できるか() {
        System.out.println("=== プレイヤー初期化テスト開始 ===");
        Player testObject = new Player(inputManager, world, 0, 2, 0);
        System.out.println("作成されたプレイヤー:");
        System.out.println("  名前: " + testObject.getName());
        System.out.println("  ID: " + testObject.getId());
        System.out.println("  位置: (" + testObject.getX() + ", " + testObject.getY() + ", " + testObject.getZ() + ")");
        
        Assertions.assertThat(testObject)
            .satisfies(player -> {
                Assertions.assertThat(player.getName()).isEqualTo("player");
                Assertions.assertThat(player.getId()).isEqualTo("Player");
                Assertions.assertThat(player.getX()).isEqualTo(0.0f);
                Assertions.assertThat(player.getY()).isEqualTo(2.0f);
                Assertions.assertThat(player.getZ()).isEqualTo(0.0f);
            });
        System.out.println("=== プレイヤー初期化テスト完了 ===\n");
    }

    @Test
    public void 衝突テスト() {
        Player testPlayer = new Player(inputManager, world, 0, 2, 0);
        Block testBlock = new Block(world, "testblock", "stone", 0, 2, 0, 1, 1, 1);
        var collider = testBlock.getBoxCollider();
        var transform = collider.getTransform();
        
        Assertions.assertThat(transform)
            .satisfies(t -> {
                Assertions.assertThat(t.getX()).isEqualTo(0);
                Assertions.assertThat(t.getY()).isEqualTo(2);
                Assertions.assertThat(t.getZ()).isEqualTo(0);
                Assertions.assertThat(t.getWidth()).isEqualTo(1);
                Assertions.assertThat(t.getHeight()).isEqualTo(1);
                Assertions.assertThat(t.getDepth()).isEqualTo(1);
            });

        Assertions.assertThat(testPlayer.checkCollisionInfo(testBlock)).isEqualTo("Collision between player and testblock: YES");
    }
}
