package uk.youkan.minicraft.world;

import java.util.ArrayList;
import java.util.List;

import uk.youkan.minicraft.entity.Mob;
import uk.youkan.minicraft.world.block.Block;
import uk.youkan.minicraft.world.block.BlockType;

/**
 * ワールドクラス - Chunk ベース管理
 */
public class World {
    private Sky sky;
    
    private final Chunk[][][] chunks;
    private final int chunksX, chunksY, chunksZ;
    
    private List<Mob> mobs = new ArrayList<>();
    private final int width, height, depth;
    private final int originX, originY, originZ;
    private final static float G = -0.007f;

    /**
     * ワールドを初期化します。
     * @param width ワールドの幅
     * @param height ワールドの高さ
     * @param depth ワールドの奥行き
     */
    public World(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.originX = -width / 2;
        this.originY = 0;
        this.originZ = -depth / 2;
        sky = new Sky(this, "空", "air", originX, originY, originZ, width * 2, height, depth * 2);
        
        chunksX = (int) Math.ceil((double) width / Chunk.CHUNK_SIZE);
        chunksY = (int) Math.ceil((double) height / Chunk.CHUNK_SIZE);
        chunksZ = (int) Math.ceil((double) depth / Chunk.CHUNK_SIZE);
        chunks = new Chunk[chunksX][chunksY][chunksZ];
        
        for (int cx = 0; cx < chunksX; cx++) {
            for (int cy = 0; cy < chunksY; cy++) {
                for (int cz = 0; cz < chunksZ; cz++) {
                    int chunkWorldX = originX + cx * Chunk.CHUNK_SIZE;
                    int chunkWorldY = originY + cy * Chunk.CHUNK_SIZE;
                    int chunkWorldZ = originZ + cz * Chunk.CHUNK_SIZE;
                    chunks[cx][cy][cz] = new Chunk(this, chunkWorldX, chunkWorldY, chunkWorldZ);
                    
                    // Chunk 内のブロックを初期化
                    for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                        for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                                int worldY = chunkWorldY + y;
                                if (worldY < 1) {
                                    chunks[cx][cy][cz].setBlock(x, y, z, BlockType.GRASS);
                                } else {
                                    chunks[cx][cy][cz].setBlock(x, y, z, BlockType.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // アクセサメソッド
    public float getG() {
        return G;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getDepth() { return depth; }
    public int getOriginX() { return originX; }
    public int getOriginY() { return originY; }
    public int getOriginZ() { return originZ; }

    /**
     * ワールドの描画ロジックを実行します。
     */
    public void render() {
        sky.render();
        
        for (int cx = 0; cx < chunksX; cx++) {
            for (int cy = 0; cy < chunksY; cy++) {
                for (int cz = 0; cz < chunksZ; cz++) {
                    chunks[cx][cy][cz].render();
                }
            }
        }
    }

    public List<Mob> getMobs() {
        return mobs;
    }

    /**
     * Chunk 配列を取得
     */
    public Chunk[][][] getChunks() {
        return chunks;
    }

    /**
     * ワールド座標から Chunk を取得
     */
    public Chunk getChunkAt(int worldX, int worldY, int worldZ) {
        // ワールド座標からチャンク配列インデックスを計算
        // Math.floorDiv を使用して負の座標も正しく処理
        int cx = Math.floorDiv(worldX - originX, Chunk.CHUNK_SIZE);
        int cy = Math.floorDiv(worldY - originY, Chunk.CHUNK_SIZE);
        int cz = Math.floorDiv(worldZ - originZ, Chunk.CHUNK_SIZE);
        
        if (cx >= 0 && cx < chunksX && cy >= 0 && cy < chunksY && cz >= 0 && cz < chunksZ) {
            return chunks[cx][cy][cz];
        }
        return null;
    }

    /**
     * ワールド座標のブロックタイプを取得（Chunk ベース）
     */
    public BlockType getBlockTypeAt(int worldX, int worldY, int worldZ) {
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);
        if (chunk != null) {
            int localX = worldX - chunk.getChunkX();
            int localY = worldY - chunk.getChunkY();
            int localZ = worldZ - chunk.getChunkZ();
            return chunk.getBlock(localX, localY, localZ);
        }
        return BlockType.AIR;
    }

    /**
     * ワールド座標のブロックタイプを設定（Chunk ベース）
     */
    public void setBlockTypeAt(int worldX, int worldY, int worldZ, BlockType blockType) {
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);
        if (chunk != null) {
            int localX = worldX - chunk.getChunkX();
            int localY = worldY - chunk.getChunkY();
            int localZ = worldZ - chunk.getChunkZ();
            chunk.setBlock(localX, localY, localZ, blockType);
        }
    }

    /**
     * ワールド座標からBlockオブジェクトを取得（Chunkベース）
     */
    public Block getBlockAt(int worldX, int worldY, int worldZ) {
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);
        if (chunk != null) {
            int localX = worldX - chunk.getChunkX();
            int localY = worldY - chunk.getChunkY();
            int localZ = worldZ - chunk.getChunkZ();
            return chunk.getBlockObject(localX, localY, localZ);
        }
        // 範囲外の場合は空気ブロックを返す
        return new Block(this, "空気ブロック", "air", worldX, worldY, worldZ, 1, 1, 1);
    }

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
}
