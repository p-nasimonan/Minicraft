package uk.youkan.minicraft.input;

import static org.lwjgl.glfw.GLFW.*;

/**
 * キーボード入力を管理するクラス
 * キーの状態（押下、リリース）を追跡します
 */
public class KeyboardInput {
    private static final int KEY_COUNT = 512;
    private final boolean[] keys = new boolean[KEY_COUNT];
    private final boolean[] keysLast = new boolean[KEY_COUNT];
    private final long windowHandle;

    public KeyboardInput(long windowHandle) {
        this.windowHandle = windowHandle;
        setupCallbacks();
    }

    /**
     * キーボードコールバックを設定
     */
    private void setupCallbacks() {
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key >= 0 && key < KEY_COUNT) {
                keys[key] = (action == GLFW_PRESS || action == GLFW_REPEAT);
            }
        });
    }

    /**
     * フレーム開始時に呼び出し（状態更新用）
     */
    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, KEY_COUNT);
    }

    /**
     * キーが押下されているかを確認
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= KEY_COUNT) return false;
        return keys[keyCode];
    }

    /**
     * キーが押された瞬間かを確認（1フレームだけtrue）
     */
    public boolean isKeyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= KEY_COUNT) return false;
        return keys[keyCode] && !keysLast[keyCode];
    }

    /**
     * キーが離された瞬間かを確認（1フレームだけtrue）
     */
    public boolean isKeyJustReleased(int keyCode) {
        if (keyCode < 0 || keyCode >= KEY_COUNT) return false;
        return !keys[keyCode] && keysLast[keyCode];
    }

    /**
     * 複数キーが全て押下されているか確認
     */
    public boolean areKeysPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (!isKeyPressed(keyCode)) return false;
        }
        return true;
    }

    /**
     * 複数キーのいずれかが押下されているか確認
     */
    public boolean isAnyKeyPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (isKeyPressed(keyCode)) return true;
        }
        return false;
    }

    /**
     * 特定のキーが押下されているか（文字列で指定）
     */
    public boolean isKeyPressed(String keyName) {
        return isKeyPressed(getKeyCode(keyName));
    }

    /**
     * 特定のキーが押された瞬間か（文字列で指定）
     */
    public boolean isKeyJustPressed(String keyName) {
        return isKeyJustPressed(getKeyCode(keyName));
    }

    /**
     * キー名からキーコードを取得
     */
    private int getKeyCode(String keyName) {
        return switch (keyName.toLowerCase()) {
            case "w" -> GLFW_KEY_W;
            case "a" -> GLFW_KEY_A;
            case "s" -> GLFW_KEY_S;
            case "d" -> GLFW_KEY_D;
            case "space" -> GLFW_KEY_SPACE;
            case "shift", "left_shift" -> GLFW_KEY_LEFT_SHIFT;
            case "ctrl", "left_ctrl" -> GLFW_KEY_LEFT_CONTROL;
            case "e" -> GLFW_KEY_E;
            case "esc" -> GLFW_KEY_ESCAPE;
            case "f3" -> GLFW_KEY_F3;
            case "enter" -> GLFW_KEY_ENTER;
            default -> -1;
        };
    }
}
