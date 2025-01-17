package jp.ac.uryukyu.ie.e245719;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class Sky extends GameObject {
    public Sky(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        this.id = id;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    @Override
    public void update() {
        // 空の更新処理
    }

    @Override
    public void render() {
        glPushMatrix();
        glTranslatef(x, y, z);

        // グラデーションの色を設定
        float[] topColor = {0.3f, 0.8f, 1.0f}; // 上の色
        float[] bottomColor = {0.7f, 0.9f, 1.0f}; // 下の色

        // 空の描画処理
        glBegin(GL_QUADS);
        drawFace("front", topColor, bottomColor);
        drawFace("back", topColor, bottomColor);
        drawFace("left", topColor, bottomColor);
        drawFace("right", topColor, bottomColor);
        drawFace("top", topColor, topColor);
        drawFace("bottom", bottomColor, bottomColor);
        glEnd();

        glPopMatrix();
    }
}
