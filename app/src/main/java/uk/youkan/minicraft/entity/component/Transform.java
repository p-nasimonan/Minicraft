package uk.youkan.minicraft.entity.component;

/**
 * 座標・位置管理コンポーネント
 * エンティティの位置、サイズ、移動を管理します
 */
public class Transform {
    private float x, y, z;
    private final float width, height, depth;

    public Transform(float x, float y, float z, float width, float height, float depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    // Position getters/setters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Size getters (immutable)
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getDepth() { return depth; }

    /**
     * 相対移動
     */
    public void translate(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    /**
     * 中心座標を取得
     */
    public float getCenterX() { return x + width / 2; }
    public float getCenterY() { return y + height / 2; }
    public float getCenterZ() { return z + depth / 2; }

    /**
     * 位置をコピー
     */
    public Transform copy() {
        return new Transform(x, y, z, width, height, depth);
    }

    @Override
    public String toString() {
        return String.format("Transform(pos=(%.2f, %.2f, %.2f), size=(%.2f, %.2f, %.2f))",
            x, y, z, width, height, depth);
    }
}
