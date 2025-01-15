package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Block> blocks;
    private final List<Enemy> enemies;
    private final static float G = -0.007f;

    public World() {
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        generate();
    }

    public final void generate() {
        // ワールドの生成ロジックをここに追加
        // 地面を石ブロックとして追加
        for (int x = -10; x < 10; x++) {
            for (int z = -10; z < 10; z++) {
                Block block = new Block("石ブロック", "stone", x * 1, -4, z * 1, 1, 1, 1);
                blocks.add(block);
            }
        } 
        // 必要に応じて他のブロックも追加
    }

    /**
     * ワールドの更新ロジックを実行します。
     * 敵、プレイヤー、ブロックの状態を一括で更新します。
     */
    public void update() {
        for (Block block : blocks) {
            block.update();
        }
    }

    public void render() {
        for (Block block : blocks) {
            block.render();
        }
    }

    // ゲームオブジェクトを操作するためのメソッド
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<Block> getBlocks() {
        return blocks;
    }
    public List<GameObject> getGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.addAll(blocks);
        gameObjects.addAll(enemies);
        return gameObjects;
    }
    public void addBlock(Block block) {
        blocks.add(block);
    }
    public void replaceBlock(Block block) {
        for (Block b : blocks) {
            if (b.x == block.x && b.y == block.y && b.z == block.z) {
                blocks.remove(b);
                blocks.add(block);
                break;
            }
        }
    }

    public float getG() {
        return G;
    }
}