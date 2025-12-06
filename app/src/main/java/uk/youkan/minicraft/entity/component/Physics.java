package uk.youkan.minicraft.entity.component;

/**
 * 物理演算コンポーネント
 * 重力、速度、接地判定を管理します
 */
public class Physics {
    private final Transform transform;
    private float vx, vy, vz;  // 速度
    private boolean onGround;
    private float gravity = -0.007f;
    private float terminalVelocity = -0.5f;

    public Physics(Transform transform) {
        this.transform = transform;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.onGround = false;
    }

    /**
     * 重力を適用
     */
    public void applyGravity() {
        if (!onGround) {
            vy += gravity;
            if (vy < terminalVelocity) {
                vy = terminalVelocity;
            }
        }
    }

    /**
     * 速度を位置に適用
     */
    public void applyVelocity() {
        transform.translate(vx, vy, vz);
    }

    /**
     * Y方向の速度のみを位置に適用
     */
    public float getNextY() {
        return transform.getY() + vy;
    }

    // Velocity getters/setters
    public float getVx() { return vx; }
    public float getVy() { return vy; }
    public float getVz() { return vz; }

    public void setVx(float vx) { this.vx = vx; }
    public void setVy(float vy) { this.vy = vy; }
    public void setVz(float vz) { this.vz = vz; }

    public void setVelocity(float vx, float vy, float vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    // Ground state
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }

    // Physics settings
    public float getGravity() { return gravity; }
    public void setGravity(float gravity) { this.gravity = gravity; }

    public float getTerminalVelocity() { return terminalVelocity; }
    public void setTerminalVelocity(float terminalVelocity) { this.terminalVelocity = terminalVelocity; }

    /**
     * 水平方向の速度をリセット
     */
    public void stopHorizontal() {
        vx = 0;
        vz = 0;
    }

    /**
     * 全ての速度をリセット
     */
    public void stop() {
        vx = 0;
        vy = 0;
        vz = 0;
    }

    /**
     * 着地処理
     */
    public void land() {
        vy = 0;
        onGround = true;
    }
}
