package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;
/**
 * キーボードとマウスの入力を管理するクラス
 */
public class InterFace {
    private final long windowHandle;
    private float mouseX, mouseY;
    private boolean isKeyPressed = false;
    private boolean MousePressed = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private int pressedKey;
    private int pressedAction;
    private MouseInput mouseInput;

    /**
     * インターフェースを初期化します
     * @param windowHandle ウィンドウハンドル
     */
    public InterFace(long windowHandle) {
        this.windowHandle = windowHandle;
        this.mouseInput = new MouseInput(windowHandle);
        setup();

    }
    /**
     * キーボードとマウスのコールバックを設定します
     */
    private void setup() {
        // マウスボタンのコールバックを設定
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
                this.MousePressed = (this.leftButtonPressed || this.rightButtonPressed);
            } else {
                this.MousePressed = false;
                this.leftButtonPressed = false;
                this.rightButtonPressed = false;
            }
        });

        // キーボードのコールバックを設定
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
        // キーボードの状態を更新
        GLFW.glfwPollEvents();
        // マウスの状態を更新
        double[] xpos = new double[1];
        double[] ypos = new double[1];
        GLFW.glfwGetCursorPos(windowHandle, xpos, ypos);

        ypos[0] = 600 - ypos[0];
        this.mouseX = (float) xpos[0];
        this.mouseY = (float) ypos[0];
    }


    // アクセサメソッド-----------------------------------------------------
    public float getMouseX() {
        return this.mouseX;
    }
    public float getMouseY() {
        return this.mouseY;
    }

    /*
     * キーボードが押されているかを取得します
     * @return キーボードが押されている場合はtrue
     */
    public boolean isKeyPressed() {
        return this.isKeyPressed;
    }

    /**
     * 指定されたキーが押されているかを取得します
     * @param key キー
     * @return キーが押されている場合はtrue
     */
    public boolean isKeyPressed(String key) {
        return switch (key) {
            case "enter" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS;
            case "space" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
            case "esc" -> GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS;
            default -> false;
        };
    }

    /**
     * 何かマウスが押されているかを取得します
     * @return マウスが押されている場合はtrue
     */
    public boolean isMousePressed() {
        return this.MousePressed;
    }
    /**
     * 左クリックが押されているかを取得します
     * @return 左クリックが押されている場合はtrue
     */
    public boolean isLeftButtonPressed() {
        return this.leftButtonPressed;
    }
    /**
     * 右クリックが押されているかを取得します
     * @return 右クリックが押されている場合はtrue
     */
    public boolean isRightButtonPressed() {
        return this.rightButtonPressed;
    }
    /**
     * 押されたキーを取得します
     * @return 押されたキー
     */
    public int getPressedKey() {
        return this.pressedKey;
    }
    /**
     * 押されたアクションを取得します
     * @return 押されたアクション
     */
    public int getPressedAction() {
        return this.pressedAction;
    }
    
    /**
     * マウス入力を設定します
     * @param mouseInput マウス入力
     */
    public void setMouseInput(MouseInput mouseInput) {
        this.mouseInput = mouseInput;
    }
    /**
     * マウス入力を取得します
     * @return マウス入力
     */
    public MouseInput getMouseInput() {
        return this.mouseInput;
    }
}
