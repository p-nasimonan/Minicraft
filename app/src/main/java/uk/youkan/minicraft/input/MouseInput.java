package uk.youkan.minicraft.input;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * マウス入力を管理するクラス
 * 位置、ボタン状態、ホイール回転を追跡します
 */
public class MouseInput {
    private Vector2f currentPos;
    private Vector2f previousPos;
    private Vector2f displVec;
    
    private boolean inWindow;
    private boolean leftButtonPressed;
    private boolean leftButtonPressedLast;
    private boolean rightButtonPressed;
    private boolean rightButtonPressedLast;
    private boolean middleButtonPressed;
    private double scrollDelta;

    public MouseInput(long windowHandle) {
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        displVec = new Vector2f();
        leftButtonPressed = false;
        leftButtonPressedLast = false;
        rightButtonPressed = false;
        rightButtonPressedLast = false;
        middleButtonPressed = false;
        inWindow = false;
        scrollDelta = 0;

        glfwSetCursorPosCallback(windowHandle, (handle, xpos, ypos) -> {
            currentPos.x = (float) xpos;
            currentPos.y = (float) ypos;
        });
        
        glfwSetCursorEnterCallback(windowHandle, (handle, entered) -> inWindow = entered);
        
        glfwSetMouseButtonCallback(windowHandle, (handle, button, action, mode) -> {
            boolean pressed = (action == GLFW_PRESS);
            switch (button) {
                case GLFW_MOUSE_BUTTON_LEFT:
                    leftButtonPressed = pressed;
                    break;
                case GLFW_MOUSE_BUTTON_RIGHT:
                    rightButtonPressed = pressed;
                    break;
                case GLFW_MOUSE_BUTTON_MIDDLE:
                    middleButtonPressed = pressed;
                    break;
            }
        });
        
        glfwSetScrollCallback(windowHandle, (handle, xoffset, yoffset) -> {
            scrollDelta = yoffset;
        });
    }

    /**
     * フレーム開始時に呼び出し（状態更新用）
     */
    public void update() {
        // 位置差分を計算
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            displVec.x = currentPos.x - previousPos.x;
            displVec.y = currentPos.y - previousPos.y;
        }
        
        previousPos.set(currentPos);
        
        // ボタン状態の前フレーム値を更新
        leftButtonPressedLast = leftButtonPressed;
        rightButtonPressedLast = rightButtonPressed;
        
        // スクロール値をリセット
        scrollDelta = 0;
    }

    // === Position getters ===

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public float getX() {
        return currentPos.x;
    }

    public float getY() {
        return currentPos.y;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    // === Button state ===

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isLeftButtonJustPressed() {
        return leftButtonPressed && !leftButtonPressedLast;
    }

    public boolean isLeftButtonJustReleased() {
        return !leftButtonPressed && leftButtonPressedLast;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public boolean isRightButtonJustPressed() {
        return rightButtonPressed && !rightButtonPressedLast;
    }

    public boolean isRightButtonJustReleased() {
        return !rightButtonPressed && rightButtonPressedLast;
    }

    public boolean isMiddleButtonPressed() {
        return middleButtonPressed;
    }

    // === Scroll ===

    public double getScrollDelta() {
        return scrollDelta;
    }

    // === Window state ===

    public boolean isInWindow() {
        return inWindow;
    }
}

