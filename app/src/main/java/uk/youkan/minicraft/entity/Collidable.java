package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.entity.component.BoxCollider;

/**
 * 衝突判定を持つオブジェクトのインターフェース
 */
public interface Collidable {
    /**
     * BoxColliderを取得します
     * @return このオブジェクトのBoxCollider
     */
    BoxCollider getBoxCollider();
    
    /**
     * 指定された位置でのブロックとの衝突判定を行います
     * @param newX 新しいX座標
     * @param newY 新しいY座標
     * @param newZ 新しいZ座標
     * @return 衝突する場合はtrue
     */
    boolean checkCollisionWithBlocks(float newX, float newY, float newZ);
}
