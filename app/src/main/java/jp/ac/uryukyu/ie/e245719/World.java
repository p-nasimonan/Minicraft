package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Block> blocks;
    private final List<Enemy> enemies;
    // GameObjectのリストを作成し、全てのオブジェクトを更新
    private List<GameObject> gameObjects = new ArrayList<>();
    private static float g = -0.01f;

    public World() {
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        gameObjects = new ArrayList<>();
        generate();
    }

    public final void generate() {
        // ワールドの生成ロジックをここに追加
        // 地面を石ブロックとして追加
        for (int x = -10; x < 10; x++) {
            for (int z = -10; z < 10; z++) {
                Block block = new Block("stone", x * 1, -4, z * 1, 1, 1, 1);
                blocks.add(block);
                gameObjects.add(block);
            }
        } 
        // 必要に応じて他のブロックも追加
    }

    /**
     * ワールドの更新ロジックを実行します。
     * 敵、プレイヤー、ブロックの状態を一括で更新します。
     */
    public void update() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    public void render() {
        for (GameObject gameObject : gameObjects) {
            gameObject.render();
        }
    }

    // ブロックリストへのアクセサを追加
    public List<Block> getBlocks() {
        return blocks;
    }
    public void addBlock(Block block) {
        blocks.add(block);
    }
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void gravity(boolean isGrounded) {
        if (!isGrounded) {
            g = -0.01f;
        } else {
            g = 0;
        }
    }

    public float getG() {
        return g;
    }
}