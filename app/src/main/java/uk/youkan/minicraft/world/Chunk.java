package uk.youkan.minicraft.world;

import uk.youkan.minicraft.world.block.Block;
import uk.youkan.minicraft.world.block.BlockType;

/**
 * Chunk クラス - ワールドを 16×16×16 のブロックチャンクで管理
 * VBO による高速レンダリングへの移行を想定した設計
 * 
 * 責務:
 * - ブロック ID の管理（メモリ効率）
 * - Block オブジェクトの生成・キャッシュ
 * - 描画は Block クラスに委譲
 */
public class Chunk {
    public static final int CHUNK_SIZE = 16;
    
    private final World world;
    private final int chunkX, chunkY, chunkZ; // チャンクのワールド座標
    private final byte[][][] blockIds; // ブロック ID (0-255)
    private final Block[][][] blocks; // Block オブジェクトキャッシュ
    private boolean isDirty = true; // メッシュ再構築が必要か
    
    // 将来の VBO 用フィールド（今は未使用）
    // private int vboId = -1;
    // private int vertexCount = 0;

    /**
     * Chunk を作成
     * @param world ワールドへの参照
     * @param chunkX チャンクの X 座標（ワールド座標）
     * @param chunkY チャンクの Y 座標（ワールド座標）
     * @param chunkZ チャンクの Z 座標（ワールド座標）
     */
    public Chunk(World world, int chunkX, int chunkY, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.blockIds = new byte[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        this.blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        
        // デフォルトで空気ブロックで初期化
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blockIds[x][y][z] = (byte) BlockType.AIR.getId();
                }
            }
        }
    }

    /**
     * チャンク内の相対座標にブロックを設定
     */
    public void setBlock(int localX, int localY, int localZ, BlockType blockType) {
        if (isValidLocalCoord(localX, localY, localZ)) {
            blockIds[localX][localY][localZ] = (byte) blockType.getId();
            blocks[localX][localY][localZ] = null; // キャッシュ無効化
            isDirty = true;
        }
    }

    /**
     * チャンク内の相対座標のブロックタイプを取得
     */
    public BlockType getBlock(int localX, int localY, int localZ) {
        if (isValidLocalCoord(localX, localY, localZ)) {
            return BlockType.fromId(blockIds[localX][localY][localZ] & 0xFF);
        }
        return BlockType.AIR;
    }

    /**
     * チャンク内座標が有効かチェック
     */
    private boolean isValidLocalCoord(int x, int y, int z) {
        return x >= 0 && x < CHUNK_SIZE && 
               y >= 0 && y < CHUNK_SIZE && 
               z >= 0 && z < CHUNK_SIZE;
    }

    /**
     * Block オブジェクトを取得（キャッシュ付き）
     */
    public Block getBlockObject(int localX, int localY, int localZ) {
        if (!isValidLocalCoord(localX, localY, localZ)) {
            return null;
        }
        
        // キャッシュにあればそれを返す
        if (blocks[localX][localY][localZ] != null) {
            return blocks[localX][localY][localZ];
        }
        
        // キャッシュになければ生成
        BlockType blockType = getBlock(localX, localY, localZ);
        float worldX = chunkX + localX;
        float worldY = chunkY + localY;
        float worldZ = chunkZ + localZ;
        
        Block block = new Block(
            world,
            blockType.getName(),
            blockType.getStringId(),
            worldX, worldY, worldZ,
            1, 1, 1
        );
        
        // 色を設定
        float[] color = blockType.getColor();
        block.getRenderer().setColor(color[0], color[1], color[2]);
        
        blocks[localX][localY][localZ] = block;
        return block;
    }

    /**
     * チャンクを描画（Block オブジェクトに委譲）
     */
    public void render() {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    BlockType blockType = getBlock(x, y, z);
                    if (!blockType.isAir()) {
                        Block block = getBlockObject(x, y, z);
                        if (block != null) {
                            block.render();
                        }
                    }
                }
            }
        }
        isDirty = false;
    }

    // アクセサ
    public int getChunkX() { return chunkX; }
    public int getChunkY() { return chunkY; }
    public int getChunkZ() { return chunkZ; }
    public boolean isDirty() { return isDirty; }
}
