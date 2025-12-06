package uk.youkan.minicraft.ui;

import org.lwjgl.opengl.GL11;
import uk.youkan.minicraft.input.InputManager;

/**
 * ゲーム内のボタンを表現するクラス
 * InputManagerを使用してクリック判定を行う
 */
public class Button {
    private final float x, y, width, height;
    private final String label;
    private boolean hovered;
    private boolean clicked;
    private Runnable onClickCallback;

    /**
     * ボタンを初期化します。
     * @param x ボタンのX座標
     * @param y ボタンのY座標
     * @param width ボタンの幅
     * @param height ボタンの高さ
     * @param label ボタンのラベル
     */
    public Button(float x, float y, float width, float height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.hovered = false;
        this.clicked = false;
    }

    /**
     * ボタンの状態を更新（入力を確認）
     */
    public void update(InputManager inputManager) {
        clicked = false;
        
        // マウスがボタン領域内にあるか確認（UI座標系）
        hovered = inputManager.isMouseInBounds(x, y, width, height);
        
        // クリック判定
        if (hovered && inputManager.isLeftButtonJustPressed()) {
            clicked = true;
            if (onClickCallback != null) {
                onClickCallback.run();
            }
        }
    }

    /**
     * ボタンを画面に描画します。
     */
    public void render() {
        // ボタンの背景
        if (clicked) {
            GL11.glColor3f(0.3f, 0.3f, 0.3f);  // クリック中
        } else if (hovered) {
            GL11.glColor3f(0.7f, 0.5f, 0.5f);  // ホバー中
        } else {
            GL11.glColor3f(0.5f, 0.5f, 0.5f);  // 通常
        }
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();

        // 再生アイコン（三角形）
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        float tr_left = x + width / 2.5f;
        float tr_half = y + height / 2;
        float tr_right = tr_left + width / 5;
        GL11.glVertex2f(tr_left, y + height / 4);
        GL11.glVertex2f(tr_left, y + 3 * height / 4);
        GL11.glVertex2f(tr_right, tr_half);
        GL11.glEnd();
    }

    /**
     * マウスがボタン上にあるかどうかを判定します。
     * @param mouseX マウスのX座標
     * @param mouseY マウスのY座標
     * @return ボタン上にマウスがある場合はtrue
     */
    public boolean isTouched(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // === Getters/Setters ===

    public String getLabel() {
        return label;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isClicked() {
        return clicked;
    }

    /**
     * クリック時のコールバックを設定
     */
    public void setOnClickCallback(Runnable callback) {
        this.onClickCallback = callback;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}

