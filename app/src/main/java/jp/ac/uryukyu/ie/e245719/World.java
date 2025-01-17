package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

public class World {
    private Sky sky;
    private final Block[][][] blocks;
    private List<Mob> mobs = new ArrayList<>();
    private final int width, height, depth;
    private  final int originX, originY, originZ;
    private final static float G = -0.007f;

    public World(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.originX = -width / 2;
        this.originY = 0;
        this.originZ = -depth / 2;
        sky = new Sky(this, "空", "air", originX, originY, originZ, width*2, height, depth*2);
        blocks = new Block[width][height][depth];
        // ブロックの初期化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (y < 1) { // 地面の高さを設定
                        blocks[x][y][z] = new Block(this, "草ブロック", "grass", x + originX, y + originY, z + originZ, 1, 1, 1);
                    } else {
                        blocks[x][y][z] = new Block(this, "空気ブロック", "air", x + originX, y + originY, z + originZ, 1, 1, 1);
                    }
                }
            
           }
        }
    }

    /**
     * ワールドの更新ロジックを実行します。
     * 敵、プレイヤー、ブロックの状態を一括で更新します。
     */
    public void update() {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    blocks[x][y][z].update();
                }
            }
        }
    }

    public void render() {
        sky.render();
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (!blocks[x][y][z].isAir()) {
                        blocks[x][y][z].render();
                    }
                }
            }
        }
    }

    // ゲームオブジェクトを操作するためのメソッド
    public Block[][][] getBlocks() {
        return blocks;
    }

    public List<Mob> getMobs() {
        return mobs;
    }

    public void replaceBlock(Block block) {
        int x = (int) toBlockX(block.x);
        int y = (int) toBlockY(block.y);
        int z = (int) toBlockZ(block.z);
        blocks[x][y][z] = block;
    }

    public float getG() {
        return G;
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getDepth(){return depth;}
    public int getOriginX() {
        return originX;
    }
    public int getOriginY() {
        return originY;
    }
    public int getOriginZ() {
        return originZ;
    }

    // ゲームの座標とブロックの座標を変換するためのメソッド
    public int toBlockX(float x) {
        int blockX = (int) (x - originX);
        if (blockX < 0) {
            blockX = 0;
        } else if (blockX >= width) {
            blockX = width - 1;
        }
        return blockX;
    }

    public int toBlockY(float y) {
        int blockY = (int) (y - originY);
        if (blockY < 0) {
            blockY = 0;
        } else if (blockY >= height) {
            blockY = height - 1;
        }
        return blockY;
    }

    public int toBlockZ(float z) {
        int blockZ = (int) (z - originZ);
        if (blockZ < 0) {
            blockZ = 0;
        } else if (blockZ >= depth) {
            blockZ = depth - 1;
        }
        return blockZ;
    }

    public Block getBlockAt(float x, float y, float z) {
        return blocks[toBlockX(x)][toBlockY(y)][toBlockZ(z)];
    }
}