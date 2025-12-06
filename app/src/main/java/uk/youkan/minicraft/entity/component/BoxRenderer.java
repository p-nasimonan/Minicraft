package uk.youkan.minicraft.entity.component;

import static org.lwjgl.opengl.GL11.*;

/**
 * 描画コンポーネント
 * 3Dボックスの描画を管理します
 */
public class BoxRenderer {
    private final Transform transform;
    private float[][] vertices;
    private float[] color = {1.0f, 1.0f, 1.0f}; // デフォルト: 白

    public BoxRenderer(Transform transform) {
        this.transform = transform;
        updateVertices();
    }

    /**
     * 頂点座標を更新（位置が変わった時に呼ぶ）
     */
    public void updateVertices() {
        float x = transform.getX();
        float y = transform.getY();
        float z = transform.getZ();
        float w = transform.getWidth();
        float h = transform.getHeight();
        float d = transform.getDepth();

        this.vertices = new float[][] {
            {x, y, z},           // 0: 前左下
            {x + w, y, z},       // 1: 前右下
            {x + w, y, z + d},   // 2: 後右下
            {x, y, z + d},       // 3: 後左下
            {x, y + h, z},       // 4: 前左上
            {x + w, y + h, z},   // 5: 前右上
            {x + w, y + h, z + d}, // 6: 後右上
            {x, y + h, z + d},   // 7: 後左上
        };
    }

    /**
     * 色を設定
     */
    public void setColor(float r, float g, float b) {
        this.color = new float[]{r, g, b};
    }

    public float[] getColor() {
        return color;
    }

    /**
     * 指定された面を描画
     */
    public void drawFace(String face) {
        int[] indices = getFaceIndices(face);
        glVertex3f(vertices[indices[0]][0], vertices[indices[0]][1], vertices[indices[0]][2]);
        glVertex3f(vertices[indices[1]][0], vertices[indices[1]][1], vertices[indices[1]][2]);
        glVertex3f(vertices[indices[2]][0], vertices[indices[2]][1], vertices[indices[2]][2]);
        glVertex3f(vertices[indices[3]][0], vertices[indices[3]][1], vertices[indices[3]][2]);
    }

    /**
     * グラデーション付きで面を描画
     */
    public void drawFace(String face, float[] topColor, float[] bottomColor) {
        int[] indices = getFaceIndices(face);
        glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        glVertex3f(vertices[indices[0]][0], vertices[indices[0]][1], vertices[indices[0]][2]);
        glVertex3f(vertices[indices[1]][0], vertices[indices[1]][1], vertices[indices[1]][2]);
        glColor3f(topColor[0], topColor[1], topColor[2]);
        glVertex3f(vertices[indices[2]][0], vertices[indices[2]][1], vertices[indices[2]][2]);
        glVertex3f(vertices[indices[3]][0], vertices[indices[3]][1], vertices[indices[3]][2]);
    }

    private int[] getFaceIndices(String face) {
        return switch (face) {
            case "front" -> new int[]{0, 1, 5, 4};
            case "back" -> new int[]{3, 2, 6, 7};
            case "left" -> new int[]{0, 3, 7, 4};
            case "right" -> new int[]{1, 2, 6, 5};
            case "top" -> new int[]{4, 5, 6, 7};
            case "bottom" -> new int[]{0, 1, 2, 3};
            default -> throw new IllegalArgumentException("Invalid face: " + face);
        };
    }

    /**
     * 辺（エッジ）を描画
     */
    public void drawEdges() {
        glColor3f(0.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        // 前面
        drawLine(vertices[0], vertices[1]);
        drawLine(vertices[1], vertices[5]);
        drawLine(vertices[5], vertices[4]);
        drawLine(vertices[4], vertices[0]);
        // 後面
        drawLine(vertices[3], vertices[2]);
        drawLine(vertices[2], vertices[6]);
        drawLine(vertices[6], vertices[7]);
        drawLine(vertices[7], vertices[3]);
        // 接続線
        drawLine(vertices[0], vertices[3]);
        drawLine(vertices[1], vertices[2]);
        drawLine(vertices[5], vertices[6]);
        drawLine(vertices[4], vertices[7]);
        glEnd();
    }

    private void drawLine(float[] start, float[] end) {
        glVertex3f(start[0], start[1], start[2]);
        glVertex3f(end[0], end[1], end[2]);
    }

    /**
     * 全ての面を描画（単色）
     */
    public void renderAllFaces() {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        drawFace("front");
        drawFace("back");
        drawFace("left");
        drawFace("right");
        drawFace("top");
        drawFace("bottom");
        glEnd();
        drawEdges();
    }
}
