package uk.youkan.minicraft.world.block;

/**
 * ブロックの種類を定義する enum
 * VBO 最適化のため、ブロックは ID で管理されます
 */
public enum BlockType {
    AIR(0, "air", "空気ブロック", new float[]{0.0f, 0.0f, 0.0f}, true),
    GRASS(1, "grass", "草ブロック", new float[]{0.0f, 0.5f, 0.0f}, false),
    STONE(2, "stone", "石ブロック", new float[]{0.3f, 0.3f, 0.3f}, false),
    DIRT(3, "dirt", "土ブロック", new float[]{0.4f, 0.3f, 0.2f}, false);

    private final int id;
    private final String stringId;
    private final String name;
    private final float[] color;
    private final boolean isAir;

    BlockType(int id, String stringId, String name, float[] color, boolean isAir) {
        this.id = id;
        this.stringId = stringId;
        this.name = name;
        this.color = color;
        this.isAir = isAir;
    }

    public int getId() {
        return id;
    }

    public String getStringId() {
        return stringId;
    }

    public String getName() {
        return name;
    }

    public float[] getColor() {
        return color;
    }

    public boolean isAir() {
        return isAir;
    }

    /**
     * 文字列 ID から BlockType を取得
     */
    public static BlockType fromStringId(String stringId) {
        for (BlockType type : values()) {
            if (type.stringId.equals(stringId)) {
                return type;
            }
        }
        return AIR; // デフォルト
    }

    /**
     * 数値 ID から BlockType を取得
     */
    public static BlockType fromId(int id) {
        for (BlockType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return AIR; // デフォルト
    }
}
