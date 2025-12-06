package uk.youkan.minicraft.ui;

import org.lwjgl.glfw.GLFW;
import uk.youkan.minicraft.input.MouseInput;

/**
 * キーボードとマウスの入力を管理するクラス
 * (旧 InterFace クラスを改名・リファクタリング)
 */
public class InputHandler {
    private final long windowHandle;
    private float mouseX, mouseY;
    private boolean isKeyPressed = false;
    private boolean mousePressed = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private int pressedKey;
    private int pressedAction;
    private MouseInput mouseInput;

    /**
     * InputHandlerを初期化します
     * @param windowHandle ウィンドウハンドル
     */
    public InputHandler(long windowHandle) {
        this.windowHandle = windowHandle;
        this.mouseInput = new MouseInput(windowHandle);
        setup();
    }

    /**
     * キーボードとマウスのコールバックを設定します
     */
    private void setup() {
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);

            ypos[0] = 600 - ypos[0];
            this.mouseX = (float) xpos[0];
            this.mouseY = (float) ypos[0];
            if (action == GLFW.GLFW_PRESS) {
                this.leftButtonPressed = (button == GLFW.GLFW_MOUSE_BUTTON_LEFT);
                this.rightButtonPressed = (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT);
                this.mousePressed = (this.leftButtonPressed || this.rightButtonPressed);
            } else {
                this.mousePressed = false;
                this.leftButtonPressed = false;
                this.rightButtonPressed = false;
            }
        });

        GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            this.pressedAction = action;
            if (action == GLFW.GLFW_PRESS) {
                this.isKeyPressed = true;
                this.pressedKey = key;
            } else {
                this.isKeyPressed = false;
            }
        });
    }

    /**
     * キーボードとマウスの状態を更新します
     */
    public void update() {
        GLFW.glfwPollEvents();
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        GLFW.glfwGetCursorPos(windowHandle, xpos, ypos);

        ypos[0] = 600 - ypos[0];
        this.mouseX = (float) xpos[0];
        this.mouseY = (float) ypos[0];
    }

    public float getMouseX() {
        return this.mouseX;
    }

    public float getMouseY() {
        return this.mouseY;
    }

    public boolean isKeyPressed() {
        return this.isKeyPressed;
    }

    public boolean isKeyPressed(String key) {
        return switch (key) {
            case "enter" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS;
            case "space" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
            case "esc" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS;
            default -> false;
        };
    }

    public boolean isMousePressed() {
        return this.mousePressed;
    }

    public boolean isLeftButtonPressed() {
        return this.leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return this.rightButtonPressed;
    }

    public int getPressedKey() {
        return this.pressedKey;
    }

    public int getPressedAction() {
        return this.pressedAction;
    }

    public void setMouseInput(MouseInput mouseInput) {
        this.mouseInput = mouseInput;
    }

    public MouseInput getMouseInput() {
        return this.mouseInput;
    }
}
