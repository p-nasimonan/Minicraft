package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class Game {
    private long window;
    private World world;
    private Player player;
    private boolean gameStarted = false;

    public void start() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        // エラーコールバックを設定
        GLFWErrorCallback.createPrint(System.err).set();

        // GLFWを初期化
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました");
        }

        // GLFWの設定
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // ウィンドウを作成
        window = GLFW.glfwCreateWindow(800, 600, "Minicraft", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("GLFWウィンドウの作成に失敗しました");
        }

        // OpenGLコンテキストを現在のものにする
        GLFW.glfwMakeContextCurrent(window);
        // 垂直同期を有効にする
        GLFW.glfwSwapInterval(1);

        // ウィンドウを表示
        GLFW.glfwShowWindow(window);

        // OpenGLを初期化
        GL.createCapabilities();

        // ゲームオブジェクトを初期化
        world = new World();
        player = new Player();

        // マウスボタンのコールバックを設定
        GLFW.glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
                double[] xpos = new double[1];
                double[] ypos = new double[1];
                GLFW.glfwGetCursorPos(window, xpos, ypos);
                handleMouseClick(xpos[0], ypos[0]);
            }
        });
    }

    private void loop() {
        // ユーザーがウィンドウを閉じようとするまでレンダリングループを実行
        while (!GLFW.glfwWindowShouldClose(window)) {
            update();
            render();

            GLFW.glfwSwapBuffers(window); // カラーバッファを交換
            GLFW.glfwPollEvents();
        }
    }

    private void update() {
        if (gameStarted) {
            // ゲームロジックを更新
            world.update();
            player.update();
        }
    }

    private void render() {
        // フレームバッファをクリア
        GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        if (gameStarted) {
            // ゲームオブジェクトをレンダリング
            world.render();
            player.render();
        } else {
            // スタートボタンをレンダリング
            renderStartButton();
        }
    }

    private void renderStartButton() {
        // 簡単なボタンをレンダリング（これはプレースホルダーです。実際のレンダリングコードはレンダリングセットアップに依存します）
        // 例えば、単純な四角形やテクスチャ付きの四角形を使用できます
        // ここではコンソールにメッセージを表示するだけです
        System.out.println("Render Start Button");
    }

    private void handleMouseClick(double xpos, double ypos) {
        // クリックがスタートボタンの範囲内にあるかどうかをチェック
        // これはプレースホルダーです。ボタンの位置とサイズに基づいて座標を調整する必要があります
        if (xpos >= 350 && xpos <= 450 && ypos >= 250 && ypos <= 350) {
            gameStarted = true;
        }
    }

    private void cleanup() {
        // ウィンドウのコールバックを解放し、ウィンドウを破棄
        GLFW.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        // GLFWを終了し、エラーコールバックを解放
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
