package uk.youkan.minicraft;

import org.junit.jupiter.api.BeforeAll;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import uk.youkan.minicraft.world.World;

public class TestBase {
    protected static long dummyWindow;
    protected static World world;

    @BeforeAll
    public static void setupGLFW() {
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
    }
}
