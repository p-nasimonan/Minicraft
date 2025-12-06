package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.physics.Collider;

/**
 * 衝突判定を持つオブジェクトのインターフェース
 */
public interface Collidable {
    /**
     * Colliderを取得します
     * @return このオブジェクトのCollider
     */
    Collider getCollider();
    
    /**
     * 指定された位置でのブロックとの衝突判定を行います
     * @param newX 新しいX座標
     * @param newY 新しいY座標
     * @param newZ 新しいZ座標
     * @return 衝突する場合はtrue
     */
    boolean checkCollisionWithBlocks(float newX, float newY, float newZ);
}
