package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;

public class InterFace {
    private final long windowHandle;
    private float mouseX, mouseY;
    private boolean isKeyPressed = false;
    private boolean isMousePressed = false;
    private int pressedKey;
    private int pressedAction;


    public InterFace(long windowHandle) {
        this.windowHandle = windowHandle;
        setup();

    }
    
    private void setup() {
        // マウスボタンのコールバックを設定
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);

            ypos[0] = 600 - ypos[0];
            mouseX = (float) xpos[0];
            mouseY = (float) ypos[0];
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
                isMousePressed = true;
            } else {
                isMousePressed = false;
            }
        });

        // キーボードのコールバックを設定
        GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            pressedAction = action;
            if (action == GLFW.GLFW_PRESS) {
                isKeyPressed = true;
                pressedKey = key;
            } else {
                isKeyPressed = false;
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
        mouseX = (float) xpos[0];
        mouseY = (float) ypos[0];
    }

    public float getMouseX() {
        return mouseX;
    }
    public float getMouseY() {
        return mouseY;
    }

    public boolean isKeyPressed() {
        return isKeyPressed;
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
        return isMousePressed;
    }
    public int getPressedKey() {
        return pressedKey;
    }
    public int getPressedAction() {
        return pressedAction;
    }
    
}
