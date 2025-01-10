package jp.ac.uryukyu.ie.e245719;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

public class Block extends Item {
    public Block(String name, String id, float x, float y, float z, float width, float height, float depth) {
        super("block", id, x, y, z, width, height, depth);
        this.id = id;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    @Override
    public void render() {
        // デプスバッファを有効化
        glEnable(GL_DEPTH_TEST);
        
        // ブロックの色を設定
        if (id.equals("stone")) {
            glColor3f(0.5f, 0.5f, 0.5f); // 灰色
        } else {
            glColor3f(1.0f, 1.0f, 1.0f); // デフォルトの色
        }

        // 面を描画
        glBegin(GL_QUADS);
        drawFace("front");
        drawFace("back");
        drawFace("left");
        drawFace("right");
        drawFace("top");
        drawFace("bottom");
        glEnd();

        // 辺を描画
        drawEdges();
        
    }

    @Override
    public boolean checkCollision(float newX, float newY, float newZ) {
        return collider.intersects(new Collider(newX, newY, newZ, 1, 1, 1));
    }
    
}
