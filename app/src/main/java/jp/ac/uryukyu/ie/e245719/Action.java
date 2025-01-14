package jp.ac.uryukyu.ie.e245719;

/**
 * ゲーム内のキャラクターの行動を定義するクラス
 */
public class Action {
    private final Mob actor;
    private static final float MOVE_SPEED = 0.1f;
    private static final float JUMP_VELOCITY = 0.2f;
    private static final float SNEAK_SPEED = 0.05f;
    private World world;

    public Action(Mob actor, World world) {
        this.actor = actor;
        this.world = world;
    }

    /**
     * 指定された方向に移動します
     * @param direction 移動方向 ("forward", "backward", "left", "right", "up", "down")
     * @param yaw プレイヤーの向いている方向（ラジアン）
     */
    public void move(String direction, float yaw) {
        double radYaw = -Math.toRadians(yaw);
        float dx = 0.0f;
        float dy = 0.0f;
        float dz = 0.0f;

        switch (direction) {
            case "forward" -> {
                dx = -(float) Math.sin(radYaw) * MOVE_SPEED;
                dz = -(float) Math.cos(radYaw) * MOVE_SPEED;
            }
            case "backward" -> {
                dx = (float) Math.sin(radYaw) * MOVE_SPEED;
                dz = (float) Math.cos(radYaw) * MOVE_SPEED;
            }
            case "left" -> {
                dx = -(float) Math.cos(radYaw) * MOVE_SPEED;
                dz = (float) Math.sin(radYaw) * MOVE_SPEED;
            }
            case "right" -> {
                dx = (float) Math.cos(radYaw) * MOVE_SPEED;
                dz = -(float) Math.sin(radYaw) * MOVE_SPEED;
            }
            case "up" -> dy = MOVE_SPEED;
            case "down" -> dy = -MOVE_SPEED;
        }

        float newX = actor.x + dx;
        float newY = actor.y + dy;
        float newZ = actor.z + dz;

        if (!actor.checkCollision(newX, newY, newZ)) {
            actor.setPosition(newX, newY, newZ);
        }
    }

    /**
     * ジャンプを実行します
     */
    public void jump() {
        if (actor.onGround) {
            actor.vy = JUMP_VELOCITY;
            actor.onGround = false;
        }
    }

    /**
     * スニーク（しゃがみ）を実行します
     */
    public void sneak() {
        // スニークの実装
    }

    /**
     * 攻撃を実行します
     */
    public void attack() {
        // 攻撃の実装
    }

    /**
     * ダメージを受けた時の処理を実行します
     */
    public void hit() {
        // 被ダメージ処理の実装
    }

    /**
     * 指定された位置にブロックを設置します
     * @param x X座標
     * @param y Y座標
     * @param z Z座標
     */
    public void putBlock(int x, int y, int z) {
        Block block = new Block("block", "stone", x, y, z, 1, 1, 1);
        actor.world.putBlock(block);
    }

    /**
     * プレイヤーの向いている方向にブロックを設置します
     * @param yaw プレイヤーの向いている方向（度数）
     */
    public void placeBlockInDirection(float yaw, String blockType) {
        float playerX = actor.x;
        float playerY = actor.y;
        float playerZ = actor.z;

        int distance = 2;
        double radYaw = Math.toRadians(yaw);
        
        float forwardX = (float) (Math.sin(radYaw)) * distance;
        float forwardZ = (float) (-Math.cos(radYaw)) * distance;

        int blockX = (int) (playerX + forwardX);
        int blockY = (int) (playerY) - 1;
        int blockZ = (int) (playerZ + forwardZ);

        // ブロックを配置するロジックをここに追加
        Block block = new Block("block", "stone", blockX, blockY, blockZ, 1, 1, 1);
       this.world.putBlock(block);
    }
}
