package jp.ac.uryukyu.ie.e245719;

import java.util.List;

public class World {
    private Block[][][] blocks;
    private List<Mob> mobs;
    private int width, height, depth;
    private int originX, originY, originZ;
    private final static float G = -0.007f;

    public World(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.originX = -width / 2;
        this.originY = -height / 2;
        this.originZ = -depth / 2;
        blocks = new Block[width][height][depth];
        // ブロックの初期化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (y == originY - 4) { // 地面の高さを設定
                        blocks[x][y][z] = new Block("石ブロック", "stone", x - originX, y - originY, z - originZ, 1, 1, 1);
                    } else {
                        blocks[x][y][z] = new Block("空気ブロック", "air", x - originX, y - originY, z - originZ, 1, 1, 1);
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
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (!blocks[x][y][z].id.equals("air")) {
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
        if (x < originX) {
            System.err.println("x座標がワールドの範囲外です。x: " + x);
        }
        return (int) (x - originX);
    }

    public int toBlockY(float y) {
        if (y < originY) {
            System.err.println("y座標がワールドの範囲外です。y: " + y);
        }
        return (int) (y - originY);
    }

    public int toBlockZ(float z) {
        if (z < originZ) {
            System.err.println("z座標がワールドの範囲外です。z: " + z);
        }
        return (int) (z - originZ);
    }
}