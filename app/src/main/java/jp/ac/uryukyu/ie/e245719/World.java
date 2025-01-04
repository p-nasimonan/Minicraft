package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

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
        // ワールドのレンダリングロジックをここに追加
        for (Block block : blocks) {
            block.render();
        }
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }
}