package jp.ac.uryukyu.ie.e245719;

/**
 * 衝突判定を管理するクラス
 */
public class Collider {
    public float x, y, z;
    public final float width, height, depth;

    /**
     * Colliderを初期化します
     * @param x X座標
     * @param y Y座標
     * @param z Z座標
     * @param width 幅
     * @param height 高さ
     * @param depth 奥行き
     */
    public Collider(float x, float y, float z, float width, float height, float depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * 他のColliderとの衝突を判定します
     * @param other 判定対象のCollider
     * @return 衝突している場合はtrue
     */
    public boolean intersects(Collider other) {
        if (other == null) {
            return false; // 何も衝突しない
        }
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y &&
               this.z < other.z + other.depth &&
               this.z + this.depth > other.z;
    }
    

    /**
     * 位置を設定します
     * @param x 新しいX座標
     * @param y 新しいY座標
     * @param z 新しいZ座標
     */
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * 特定の方向への移動が可能かチェックします
     * @param dx X方向の移動量
     * @param dy Y方向の移動量
     * @param dz Z方向の移動量
     * @param other 判定対象のCollider
     * @return 移動可能な場合はtrue
     */
    public boolean canMove(float dx, float dy, float dz, Collider other) {
        float newX = this.x + dx;
        float newY = this.y + dy;
        float newZ = this.z + dz;

        return !(newX < other.x + other.width &&
                newX + this.width > other.x &&
                newY < other.y + other.height &&
                newY + this.height > other.y &&
                newZ < other.z + other.depth &&
                newZ + this.depth > other.z);
    }

    /**
     * 境界ボックスの中心座標を取得します
     * @return 中心座標の配列 [x, y, z]
     */
    public float[] getCenter() {
        return new float[] {
            x + width/2,
            y + height/2,
            z + depth/2
        };
    }


    /**
     * 衝突時の押し戻し量を計算します
     * @param other 衝突相手のCollider
     * @return 押し戻し量の配列 [dx, dy, dz]
     */
    public float[] getResolutionVector(Collider other) {
        float[] resolution = new float[3];
        
        float dx = (this.x + this.width/2) - (other.x + other.width/2);
        float dy = (this.y + this.height/2) - (other.y + other.height/2);
        float dz = (this.z + this.depth/2) - (other.z + other.depth/2);
        
        float overlapX = (this.width + other.width)/2 - Math.abs(dx);
        float overlapY = (this.height + other.height)/2 - Math.abs(dy);
        float overlapZ = (this.depth + other.depth)/2 - Math.abs(dz);
        
        // 最小の重なりがある軸方向に押し戻し
        if (overlapX < overlapY && overlapX < overlapZ) {
            resolution[0] = dx > 0 ? overlapX : -overlapX;
        } else if (overlapY < overlapZ) {
            resolution[1] = dy > 0 ? overlapY : -overlapY;
        } else {
            resolution[2] = dz > 0 ? overlapZ : -overlapZ;
        }
        
        return resolution;
    }

}
