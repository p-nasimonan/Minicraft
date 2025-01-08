package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import org.lwjgl.system.MemoryUtil;

public class Game {
    private long window;
    private World world;
    private Player player;
    private boolean gameStarted = false;
    private Button startButton;
    private boolean cursorEnabled = true;

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

        // 座標系を設定
        setupOrtho();

        // ゲームオブジェクトを初期化
        world = new World();
        player = new Player(window);

        // スタートボタンを初期化
        startButton = new Button(350, 250, 100, 50, "Start");
        interfaceSetup();
    }

    private void interfaceSetup() {
        // マウスボタンのコールバックを設定
        GLFW.glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
                double[] xpos = new double[1];
                double[] ypos = new double[1];
                GLFW.glfwGetCursorPos(window, xpos, ypos);
                handleMouseClick(xpos[0], ypos[0]);
            }
        });

        // キーボードのコールバックを設定
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                if (gameStarted) {
                    cursorEnabled = !cursorEnabled;
                    if (cursorEnabled) {
                        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
                    } else {
                        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
                        GLFW.glfwSetCursorPos(window, 400, 300);
                    }
                }
            }
        });
    }

    private void setupOrtho() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, -1, 1); // 左, 右, 下, 上, 近, 遠
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void setupCamera() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float fov = 45.0f;
        float aspect = 800.0f / 600.0f;
        float zNear = 0.1f;
        float zFar = 100.0f;
        float fH = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
        float fW = fH * aspect;
        glFrustum(-fW, fW, -fH, fH, zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
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
            if (!cursorEnabled) {
                player.update(); // カーソルが非表示の時だけプレイヤーの視点を更新
            }
        } else {
            // マウスカーソルの位置を取得
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);

            // Y座標を反転させる
            ypos[0] = 600 - ypos[0];

            startButton.setHovered(startButton.isTouched(xpos[0], ypos[0]));
        }
    }

    private void render() {
        // フレームバッファをクリア
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (gameStarted) {
            // カメラを設定
            glLoadIdentity();
            player.setCamera();

            // ワールドをレンダリング
            world.render();
            // プレイヤーをレンダリング
            player.render();
        } else {
            // スタートボタンをレンダリング
            startButton.render();
        }
    }

    private void handleMouseClick(double xpos, double ypos) {
        if(gameStarted) {
            // クリックがプレイヤーのインベントリ内にあるかどうかをチェック
            //player.checkInventoryClick(xpos, ypos);
            //return;
        }
        else {
            // Y座標を反転させる
            ypos = 600 - ypos;
            // クリックがスタートボタンの範囲内にあるかどうかをチェック
            if (startButton.isTouched(xpos, ypos)) {
                gamestart();

            }
        }


    }

    private void gamestart() {
        gameStarted = true;
        setupCamera();
        
        // ゲーム開始時にマウスカーソルを非表示にし、中央に固定
        cursorEnabled = false;
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPos(window, 400, 300);
    }

    private void cleanup() {
        // ウィンドウのコールバックを解放し、ウィンドウを破棄
        GLFW.glfwSetWindowShouldClose(window, true);
        GLFW.glfwDestroyWindow(window);

        // GLFWを終了し、エラーコールバックを解放
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }


}
