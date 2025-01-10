package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.opengl.GL11;

public class Button {
    private final float x, y, width, height;
    private final String label;
    private boolean hovered;

    public Button(float x, float y, float width, float height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.hovered = false;
    }

    public void render() {
        // ボタンの背景を描画
        GL11.glColor3f(hovered ? 0.7f : 0.5f, 0.5f, 0.5f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();

        // 三角形の再生アイコンを描画
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

    public boolean isTouched(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public String getLabel() {
        return label;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}