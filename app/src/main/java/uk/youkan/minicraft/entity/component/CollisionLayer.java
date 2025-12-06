package uk.youkan.minicraft.entity.component;

/**
 * 衝突レイヤーを定義する列挙型
 * エンティティがどのレイヤーに属するかを決定します
 */
public enum CollisionLayer {
    NONE,       // 衝突判定なし
    BLOCK,      // ブロック（地形）
    MOB,        // モブ（プレイヤー、敵など）
    ITEM,       // アイテム（拾えるもの）
    PROJECTILE  // 投射物（矢など）
}
