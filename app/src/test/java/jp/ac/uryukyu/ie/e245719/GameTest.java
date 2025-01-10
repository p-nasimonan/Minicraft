package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import static org.junit.jupiter.api.Assertions.*;

class GameTest extends TestBase {

    @Test
    void ゲームが正しく初期化される() {
        Game game = new Game();
        assertDoesNotThrow(() -> {
            long window = game.createWindow();
            assertNotEquals(0, window);
        });
    }

    @Test
    void ウィンドウが正しく作成される() {
        Game game = new Game();
        long window = game.createWindow();
        assertNotEquals(0, window);
        GLFW.glfwDestroyWindow(window);
    }
}