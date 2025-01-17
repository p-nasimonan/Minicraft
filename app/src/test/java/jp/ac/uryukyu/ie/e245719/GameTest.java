package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

class GameTest extends TestBase {

    @Test
    void ゲームが正しく初期化される() {
        System.out.println("=== ゲーム初期化テスト開始 ===");
        Game game = new Game();
        System.out.println("ゲームインスタンスを作成");
        assertDoesNotThrow(() -> {
            System.out.println("ウィンドウ作成を試行");
            long window = game.createWindow();
            System.out.println("作成されたウィンドウハンドル: " + window);
            assertNotEquals(0, window);
        });
        System.out.println("=== ゲーム初期化テスト完了 ===\n");
    }

    @Test
    void ウィンドウが正しく作成される() {
        Game game = new Game();
        long window = game.createWindow();
        assertNotEquals(0, window);
        GLFW.glfwDestroyWindow(window);
    }
}