package uk.youkan.minicraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import uk.youkan.minicraft.entity.Player;
import uk.youkan.minicraft.input.InputManager;
import uk.youkan.minicraft.ui.Button;
import uk.youkan.minicraft.world.World;

import static org.lwjgl.opengl.GL11.*;

/**
 * ゲームの中心となるクラス。ウィンドウを作成し、ゲームの初期化、更新、描画を行う。
 */
public class Game {
    private long mainWindow;
    private World world;
    private Player player;
    private boolean gameStarted = false;
    private Button startButton;
    private boolean cursorEnabled = true;
    private InputManager inputManager;
    private boolean cursorToggleInProgress = false;
    private long lastFpsTime;
    private int fps;
    private int frames;

    /**
     * ゲームを開始します
     */
    public void start() {
        this.mainWindow = createWindow();
        gameInit();
        loop();
        cleanup();
    }

    /**
     * ウィンドウを作成します
     * @return ウィンドウハンドル
     */
    public long createWindow() {
        long win;
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFWの初期化に失敗しました");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        win = GLFW.glfwCreateWindow(800, 600, "Minicraft", MemoryUtil.NULL, MemoryUtil.NULL);
        if (win == MemoryUtil.NULL) {
            throw new RuntimeException("GLFWウィンドウの作成に失敗しました");
        }

        GLFW.glfwMakeContextCurrent(win);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(win);

        GL.createCapabilities();
        setupOrtho();

        GLFW.glfwSetFramebufferSizeCallback(win, (window, width, height) -> {
            glViewport(0, 0, width, height);
            updateProjection(width, height);
        });

        return win;
    }

    /**
     * ゲームの初期化を行います
     */
    private void gameInit() {
        this.startButton = new Button(350, 250, 100, 50, "Start");
        this.inputManager = new InputManager(mainWindow);
        this.inputManager.setUIScreenHeight(600.0f);
        this.world = new World(64, 50, 64);
        this.player = new Player(inputManager, world, 0, 5, 0);

        lastFpsTime = System.currentTimeMillis();
        fps = 0;
        frames = 0;
    }

    /**
     * ウィンドウの座標系を設定します
     */
    private void setupOrtho() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    /**
     * ウィンドウの座標系を更新します
     */
    private void updateProjection(int width, int height) {
        float aspectRatio = (float) width / height;

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        if (gameStarted) {
            float fov = 45.0f;
            float zNear = 0.1f;
            float zFar = 100.0f;
            float fH = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
            float fW = fH * aspectRatio;
            glFrustum(-fW, fW, -fH, fH, zNear, zFar);
        } else {
            glOrtho(0, width, 0, height, -1, 1);
            // UI座標系の高さを更新
            if (inputManager != null) {
                inputManager.setUIScreenHeight(height);
            }
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

    /**
     * ゲームループを開始します
     */
    private void loop() {
        double secsPerUpdate = 1.0 / 60.0;
        double previous = System.currentTimeMillis() / 1000.0;
        double steps = 0.0;

        while (!GLFW.glfwWindowShouldClose(mainWindow)) {
            double current = System.currentTimeMillis() / 1000.0;
            double elapsed = current - previous;
            previous = current;
            steps += elapsed;

            frames++;
            if (System.currentTimeMillis() - lastFpsTime > 1000) {
                fps = frames;
                frames = 0;
                lastFpsTime = System.currentTimeMillis();
            }

            while (steps >= secsPerUpdate) {
                update();
                steps -= secsPerUpdate;
            }

            render();

            GLFW.glfwSwapBuffers(mainWindow);
            GLFW.glfwPollEvents();
        }
    }

    /**
     * ゲームの状態を更新します
     */
    private void update() {
        inputManager.update();
        handleInput();
        if (gameStarted) {
            world.update();

            if (!cursorEnabled) {
                player.update();
                player.handleInput();
            }
        }
    }

    /**
     * ゲームを描画します
     */
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (gameStarted) {
            glLoadIdentity();
            player.setCamera();
            world.render();
            player.render();
        } else {
            startButton.render();
        }

        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + error);
        }
    }

    /**
     * キーボード入力を処理します
     */
    private void handleInput() {
        if (gameStarted) {
            if (inputManager.isKeyPressed("esc")) {
                toggleCursor();
            }
        } else {
            float mouseX = inputManager.getUIMouseX();
            float mouseY = inputManager.getUIMouseY();
            startButton.setHovered(startButton.isTouched(mouseX, mouseY));

            if (inputManager.isKeyPressed("enter")) {
                gamestart();
            }
            if (startButton.isTouched(mouseX, mouseY) && inputManager.isLeftButtonPressed()) {
                gamestart();
            }
        }
    }

    private void toggleCursor() {
        if (cursorToggleInProgress) return;

        cursorToggleInProgress = true;
        cursorEnabled = !cursorEnabled;

        if (cursorEnabled) {
            GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        } else {
            GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        }

        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println("カーソル切り替え中にエラーが発生しました: " + e.getMessage());
            } finally {
                cursorToggleInProgress = false;
            }
        }).start();
    }

    /**
     * ゲームを開始します
     */
    private void gamestart() {
        gameStarted = true;
        setupCamera();

        cursorEnabled = false;
        GLFW.glfwSetInputMode(mainWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwSetCursorPos(mainWindow, 400, 300);
    }

    /**
     * ゲームの終了処理を行います
     */
    public void cleanup() {
        GLFW.glfwSetWindowShouldClose(mainWindow, true);
        GLFW.glfwDestroyWindow(mainWindow);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
