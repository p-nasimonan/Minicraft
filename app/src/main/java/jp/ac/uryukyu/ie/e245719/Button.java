package jp.ac.uryukyu.ie.e245719;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Button {
    private float x, y, width, height;
    private String label;
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
        glColor3f(hovered ? 0.7f : 0.5f, 0.5f, 0.5f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();

        // 三角形の再生アイコンを描画
        glColor3f(1.0f, 1.0f, 1.0f);
        glBegin(GL_TRIANGLES);
        float tr_left = x + width / 2.5f;
        float tr_half = y + height / 2;
        float tr_right = tr_left + width / 5;
        glVertex2f(tr_left, y + height / 4);
        glVertex2f(tr_left, y + 3 * height / 4);
        glVertex2f(tr_right, tr_half);
        glEnd();
    }

    public boolean isTouched(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}