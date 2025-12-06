package uk.youkan.minicraft.entity.component;

/**
 * 当たり判定コンポーネント
 * Transformと連動して衝突判定を行います
 */
public class BoxCollider {
    private final Transform transform;

    public BoxCollider(Transform transform) {
        this.transform = transform;
    }

    /**
     * 他のBoxColliderとの衝突を判定
     */
    public boolean intersects(BoxCollider other) {
        if (other == null) return false;

        float x1 = transform.getX();
        float y1 = transform.getY();
        float z1 = transform.getZ();
        float w1 = transform.getWidth();
        float h1 = transform.getHeight();
        float d1 = transform.getDepth();

        float x2 = other.transform.getX();
        float y2 = other.transform.getY();
        float z2 = other.transform.getZ();
        float w2 = other.transform.getWidth();
        float h2 = other.transform.getHeight();
        float d2 = other.transform.getDepth();

        return x1 < x2 + w2 && x1 + w1 > x2 &&
               y1 < y2 + h2 && y1 + h1 > y2 &&
               z1 < z2 + d2 && z1 + d1 > z2;
    }

    /**
     * 指定位置での衝突判定（移動前チェック用）
     */
    public boolean wouldIntersectAt(float newX, float newY, float newZ, BoxCollider other) {
        if (other == null) return false;

        float w1 = transform.getWidth();
        float h1 = transform.getHeight();
        float d1 = transform.getDepth();

        float x2 = other.transform.getX();
        float y2 = other.transform.getY();
        float z2 = other.transform.getZ();
        float w2 = other.transform.getWidth();
        float h2 = other.transform.getHeight();
        float d2 = other.transform.getDepth();

        return newX < x2 + w2 && newX + w1 > x2 &&
               newY < y2 + h2 && newY + h1 > y2 &&
               newZ < z2 + d2 && newZ + d1 > z2;
    }

    /**
     * 移動可能かチェック
     */
    public boolean canMove(float dx, float dy, float dz, BoxCollider other) {
        float newX = transform.getX() + dx;
        float newY = transform.getY() + dy;
        float newZ = transform.getZ() + dz;
        return !wouldIntersectAt(newX, newY, newZ, other);
    }

    /**
     * 中心座標を取得
     */
    public float[] getCenter() {
        return new float[] {
            transform.getCenterX(),
            transform.getCenterY(),
            transform.getCenterZ()
        };
    }

    /**
     * 衝突時の押し戻しベクトルを計算
     */
    public float[] getResolutionVector(BoxCollider other) {
        float[] resolution = new float[3];

        float dx = transform.getCenterX() - other.transform.getCenterX();
        float dy = transform.getCenterY() - other.transform.getCenterY();
        float dz = transform.getCenterZ() - other.transform.getCenterZ();

        float overlapX = (transform.getWidth() + other.transform.getWidth()) / 2 - Math.abs(dx);
        float overlapY = (transform.getHeight() + other.transform.getHeight()) / 2 - Math.abs(dy);
        float overlapZ = (transform.getDepth() + other.transform.getDepth()) / 2 - Math.abs(dz);

        if (overlapX < overlapY && overlapX < overlapZ) {
            resolution[0] = dx > 0 ? overlapX : -overlapX;
        } else if (overlapY < overlapZ) {
            resolution[1] = dy > 0 ? overlapY : -overlapY;
        } else {
            resolution[2] = dz > 0 ? overlapZ : -overlapZ;
        }

        return resolution;
    }

    public Transform getTransform() {
        return transform;
    }
}
