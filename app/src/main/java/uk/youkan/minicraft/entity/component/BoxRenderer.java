package uk.youkan.minicraft.entity.component;

import static org.lwjgl.opengl.GL11.*;

/**
 * 描画コンポーネント
 * 3Dボックスの描画を管理します
 * 
 * 重要: 頂点座標は (0,0,0) を基準に計算し、glTranslatef で移動させます
 * これにより座標同期の問題を回避できます
 */
public class BoxRenderer {
    private final Transform transform;
    private float[] color = {1.0f, 1.0f, 1.0f}; // デフォルト: 白

    public BoxRenderer(Transform transform) {
        this.transform = transform;
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
     * 指定された面を描画（相対座標を使用）
     */
    public void drawFace(String face) {
        float w = transform.getWidth();
        float h = transform.getHeight();
        float d = transform.getDepth();

        float[][] relativeVertices = {
            {0, 0, 0},       // 0: 前左下
            {w, 0, 0},       // 1: 前右下
            {w, 0, d},       // 2: 後右下
            {0, 0, d},       // 3: 後左下
            {0, h, 0},       // 4: 前左上
            {w, h, 0},       // 5: 前右上
            {w, h, d},       // 6: 後右上
            {0, h, d},       // 7: 後左上
        };

        int[] indices = getFaceIndices(face);
        glVertex3f(relativeVertices[indices[0]][0], relativeVertices[indices[0]][1], relativeVertices[indices[0]][2]);
        glVertex3f(relativeVertices[indices[1]][0], relativeVertices[indices[1]][1], relativeVertices[indices[1]][2]);
        glVertex3f(relativeVertices[indices[2]][0], relativeVertices[indices[2]][1], relativeVertices[indices[2]][2]);
        glVertex3f(relativeVertices[indices[3]][0], relativeVertices[indices[3]][1], relativeVertices[indices[3]][2]);
    }

    /**
     * グラデーション付きで面を描画（相対座標を使用）
     */
    public void drawFace(String face, float[] topColor, float[] bottomColor) {
        float w = transform.getWidth();
        float h = transform.getHeight();
        float d = transform.getDepth();

        float[][] relativeVertices = {
            {0, 0, 0},       // 0: 前左下
            {w, 0, 0},       // 1: 前右下
            {w, 0, d},       // 2: 後右下
            {0, 0, d},       // 3: 後左下
            {0, h, 0},       // 4: 前左上
            {w, h, 0},       // 5: 前右上
            {w, h, d},       // 6: 後右上
            {0, h, d},       // 7: 後左上
        };

        int[] indices = getFaceIndices(face);
        glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        glVertex3f(relativeVertices[indices[0]][0], relativeVertices[indices[0]][1], relativeVertices[indices[0]][2]);
        glVertex3f(relativeVertices[indices[1]][0], relativeVertices[indices[1]][1], relativeVertices[indices[1]][2]);
        glColor3f(topColor[0], topColor[1], topColor[2]);
        glVertex3f(relativeVertices[indices[2]][0], relativeVertices[indices[2]][1], relativeVertices[indices[2]][2]);
        glVertex3f(relativeVertices[indices[3]][0], relativeVertices[indices[3]][1], relativeVertices[indices[3]][2]);
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
        float w = transform.getWidth();
        float h = transform.getHeight();
        float d = transform.getDepth();

        glColor3f(0.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        // 前面
        drawLine(new float[]{0, 0, 0}, new float[]{w, 0, 0});
        drawLine(new float[]{w, 0, 0}, new float[]{w, h, 0});
        drawLine(new float[]{w, h, 0}, new float[]{0, h, 0});
        drawLine(new float[]{0, h, 0}, new float[]{0, 0, 0});
        // 後面
        drawLine(new float[]{0, 0, d}, new float[]{w, 0, d});
        drawLine(new float[]{w, 0, d}, new float[]{w, h, d});
        drawLine(new float[]{w, h, d}, new float[]{0, h, d});
        drawLine(new float[]{0, h, d}, new float[]{0, 0, d});
        // 縦線
        drawLine(new float[]{0, 0, 0}, new float[]{0, 0, d});
        drawLine(new float[]{w, 0, 0}, new float[]{w, 0, d});
        drawLine(new float[]{w, h, 0}, new float[]{w, h, d});
        drawLine(new float[]{0, h, 0}, new float[]{0, h, d});
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
        glPushMatrix();
        glTranslatef(transform.getX(), transform.getY(), transform.getZ());
        
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
        
        glPopMatrix();
    }
}
