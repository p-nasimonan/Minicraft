package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.BeforeAll;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class TestBase {
    protected static long dummyWindow;
    protected static World world;

    @BeforeAll
    public static void setupGLFW() {
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
        world = new World(10, 10, 10);
    }
} 