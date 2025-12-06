package uk.youkan.minicraft.input;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 入力管理の統括クラス
 * キーボード、マウスの入力を一元管理します
 * UI描画とゲーム描画で座標系を分離
 */
public class InputManager {
    private final long windowHandle;
    private final KeyboardInput keyboard;
    private final MouseInput mouse;
    private float uiScreenHeight = 600.0f;  // UI描画用スクリーン高さ

    public InputManager(long windowHandle) {
        this.windowHandle = windowHandle;
        this.keyboard = new KeyboardInput(windowHandle);
        this.mouse = new MouseInput(windowHandle);
    }

    /**
     * フレーム開始時に呼び出し（全入力を更新）
     */
    public void update() {
        glfwPollEvents();
        keyboard.update();
        mouse.update();
    }

    /**
     * UI描画用のスクリーン高さを設定
     */
    public void setUIScreenHeight(float height) {
        this.uiScreenHeight = height;
    }

    // === Keyboard ===

    public KeyboardInput getKeyboard() {
        return keyboard;
    }

    public boolean isKeyPressed(int keyCode) {
        return keyboard.isKeyPressed(keyCode);
    }

    public boolean isKeyPressed(String keyName) {
        return keyboard.isKeyPressed(keyName);
    }

    public boolean isKeyJustPressed(int keyCode) {
        return keyboard.isKeyJustPressed(keyCode);
    }

    public boolean isKeyJustPressed(String keyName) {
        return keyboard.isKeyJustPressed(keyName);
    }

    public boolean areKeysPressed(int... keyCodes) {
        return keyboard.areKeysPressed(keyCodes);
    }

    // === Mouse Raw Coordinates ===

    public MouseInput getMouse() {
        return mouse;
    }

    public boolean isLeftButtonPressed() {
        return mouse.isLeftButtonPressed();
    }

    public boolean isLeftButtonJustPressed() {
        return mouse.isLeftButtonJustPressed();
    }

    public boolean isRightButtonPressed() {
        return mouse.isRightButtonPressed();
    }

    public boolean isRightButtonJustPressed() {
        return mouse.isRightButtonJustPressed();
    }

    public float getMouseX() {
        return mouse.getX();
    }

    public float getMouseY() {
        return mouse.getY();
    }

    // === UI Coordinate System (Y-axis flipped) ===

    /**
     * UI画面用のマウスX座標を取得
     */
    public float getUIMouseX() {
        return mouse.getX();
    }

    /**
     * UI画面用のマウスY座標を取得（Y反転）
     */
    public float getUIMouseY() {
        return uiScreenHeight - mouse.getY();
    }

    /**
     * マウスが指定領域内にあるか確認（UI座標系）
     */
    public boolean isMouseInBounds(float x, float y, float width, float height) {
        float uiX = getUIMouseX();
        float uiY = getUIMouseY();
        return uiX >= x && uiX <= x + width &&
               uiY >= y && uiY <= y + height;
    }
}
