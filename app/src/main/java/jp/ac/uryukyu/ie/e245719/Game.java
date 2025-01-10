package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.system.MemoryUtil;

public class Game {
    private long mainWindow;
    private World world;
    private Player player;
    private boolean gameStarted = false;
    private Button startButton;
    private boolean cursorEnabled = true;
    private InterFace interFace;
    private boolean cursorToggleInProgress = false; // カーソル切り替え中フラグ
    private long lastFpsTime;  // 前回のFPS計測時刻
    private int fps;          // 現在のFPS
    private int frames;       // フレームカウンター

    public void start() {
        this.mainWindow = createWindow();
        gameInit();
        loop();
        cleanup();
    }

    private long createWindow() {
        // スレッドチェックを追加
        if (!Thread.currentThread().getName().equals("main")) {
            throw new IllegalStateException("GLFWはメインスレッドでのみ使用できます。JVMを-XstartOnFirstThreadで起動してください。");
        }
        long win;
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
        win = GLFW.glfwCreateWindow(800, 600, "Minicraft", MemoryUtil.NULL, MemoryUtil.NULL);
        if (win == MemoryUtil.NULL) {
            throw new RuntimeException("GLFWウィンドウの作成に失敗しました");
        }

        // OpenGLコンテキストを現在のものにする
        GLFW.glfwMakeContextCurrent(win);
        // 垂直同期を有効にする
        GLFW.glfwSwapInterval(1);

        // ウィンドウを表示
        GLFW.glfwShowWindow(win);

        // OpenGLを初期化
        GL.createCapabilities();

        // 座標系を設定
        setupOrtho();

        // ウィンドウリサイズのコールバックを追加
        GLFW.glfwSetFramebufferSizeCallback(win, (window, width, height) -> {
            glViewport(0, 0, width, height);
            updateProjection(width, height);
        });

        return win;
    }

    private void gameInit() {
        // ゲームオブジェクトを初期化
        this.world = new World();
        this.player = new Player(mainWindow, world, 0, 2, 0);

        // スタートボタンを初期化
        this.startButton = new Button(350, 250, 100, 50, "Start");

        // インターフェースを初期化
        this.interFace = new InterFace(mainWindow);

        // FPS計測の初期化
        lastFpsTime = System.currentTimeMillis();
        fps = 0;
        frames = 0;
    }

    private void setupOrtho() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, -1, 1); // 左, 右, 下, 上, 近, 遠
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void updateProjection(int width, int height) {
        float aspectRatio = (float) width / height;
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        if (gameStarted) {
            // 3Dビュー用の設定
            float fov = 45.0f;
            float zNear = 0.1f;
            float zFar = 100.0f;
            float fH = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
            float fW = fH * aspectRatio;
            glFrustum(-fW, fW, -fH, fH, zNear, zFar);
        } else {
            // 2Dメニュー用の設定
            float scaleFactor = 600.0f / height;
            glOrtho(0, width * scaleFactor, 0, 600, -1, 1);
        }
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void setupCamera() {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetFramebufferSize(mainWindow, width, height);
        updateProjection(width[0], height[0]);
    }

    private void loop() {
        // ゲームループのタイミング制御用
        double secsPerUpdate = 1.0 / 60.0;
        double previous = System.currentTimeMillis() / 1000.0;
        double steps = 0.0;
        
        while (!GLFW.glfwWindowShouldClose(mainWindow)) {
            double current = System.currentTimeMillis() / 1000.0;
            double elapsed = current - previous;
            previous = current;
            steps += elapsed;

            // FPS計測
            frames++;
            if (System.currentTimeMillis() - lastFpsTime > 1000) {
                fps = frames;
                frames = 0;
                lastFpsTime = System.currentTimeMillis();
                // FPSをコンソールに出力（デバッグ用）
                System.out.println("FPS: " + fps);
            }

            // 入力処理
            handleInput();

            // 固定時間ステップでの更新
            while (steps >= secsPerUpdate) {
                update();
                steps -= secsPerUpdate;
            }

            // 描画
            render();

            // バッファの入れ替えとイベント処理
            GLFW.glfwSwapBuffers(mainWindow);
            GLFW.glfwPollEvents();
        }
    }

    private void update() {
        // インターフェースの状態を更新
        interFace.update();
        handleInput();
        if (gameStarted) {
            // ゲームロジックを更新
            world.update();
            player.handleInput(interFace.getPressedKey(), interFace.getPressedAction(), interFace.getMouseX(), interFace.getMouseY(), interFace.isMousePressed());
            
            if (!cursorEnabled) {
                player.update(); // カーソルが非表示の時だけプレイヤーの視点を更新
            }
            
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

        // OpenGLエラーをチェック
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + error);
        }
    }

    private void handleInput() {
        if(gameStarted) {
            if (interFace.isKeyPressed("esc")) {
                // ゲーム中にESCキーが押されたら、カーソルを表示してゲームを一時停止
                toggleCursor();
            }
        } else {
            float mouseX = interFace.getMouseX();
            float mouseY = interFace.getMouseY();
            startButton.setHovered(startButton.isTouched(mouseX, mouseY));
    
            if (interFace.isKeyPressed("enter")) {
                gamestart();
            }
            if (startButton.isTouched(mouseX, mouseY) && interFace.isMousePressed()) {
                gamestart();
            }
        }

    }

    private void toggleCursor() {
        if (cursorToggleInProgress) return; // 連打防止

        cursorToggleInProgress = true; // 切り替え中フラグを立てる
        cursorEnabled = !cursorEnabled; // カーソルの表示状態を切り替え
        
        if (cursorEnabled) {
            GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        } else {
            GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        }

        // 一定時間後にフラグをリセット
        new Thread(() -> {
            try {
                Thread.sleep(300); // 300ミリ秒待機
            } catch (InterruptedException e) {
                System.err.println("カーソル切り替え中にエラーが発生しました: " + e.getMessage());
            } finally {
                cursorToggleInProgress = false; // 切り替え完了
            }
        }).start();
    }


    private void gamestart() {
        gameStarted = true;
        setupCamera();
        
        // ゲーム開始時にマウスカーソルを非表示にし、中央に固定
        cursorEnabled = false;
        GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPos(mainWindow, 400, 300);
    }

    private void cleanup() {
        // ウィンドウのコールバックを解放し、ウィンドウを破棄
        GLFW.glfwSetWindowShouldClose(mainWindow, true);
        GLFW.glfwDestroyWindow(mainWindow);

        // GLFWを終了し、エラーコールバックを解放
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }


}
