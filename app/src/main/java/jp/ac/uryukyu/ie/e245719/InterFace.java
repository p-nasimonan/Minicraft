package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;

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


    public InterFace(long windowHandle) {
        this.windowHandle = windowHandle;
        this.mouseInput = new MouseInput(windowHandle);
        setup();

    }
    
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
        return this.MousePressed;
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
