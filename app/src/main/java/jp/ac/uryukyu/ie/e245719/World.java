package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class World {
    private List<Block> blocks;
    private List<Enemy> enemies;

    public World() {
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        generate();
    }

    public void generate() {
        // ワールドの生成ロジックをここに追加
        // 例: ブロックや敵をリストに追加
    }

    public void update() {
        // ワールドの更新ロジックをここに追加
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    public void render() {
        // 地面の描画
        glColor3f(0.2f, 0.6f, 0.2f); // 緑色
        glBegin(GL_QUADS);
        float size = 100.0f;  // 地面のサイズ
        glVertex3f(-size, -0.5f, -size);
        glVertex3f(size, -0.5f, -size);
        glVertex3f(size, -0.5f, size);
        glVertex3f(-size, -0.5f, size);
        glEnd();

        // グリッドの描画（オプション）
        glColor3f(0.3f, 0.3f, 0.3f);
        glBegin(GL_LINES);
        for (float i = -size; i <= size; i += 2.0f) {
            glVertex3f(i, -0.5f, -size);
            glVertex3f(i, -0.5f, size);
            glVertex3f(-size, -0.5f, i);
            glVertex3f(size, -0.5f, i);
        }
        glEnd();

        // 既存のブロックと敵の描画
        for (Block block : blocks) {
            block.render();
        }
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }
}