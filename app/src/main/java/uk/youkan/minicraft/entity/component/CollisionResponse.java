package uk.youkan.minicraft.entity.component;

/**
 * 衝突時の応答タイプを定義する列挙型
 */
public enum CollisionResponse {
    NONE,       // 何もしない（通過）
    BLOCK,      // 移動をブロック（通れない）
    OVERLAP,    // 重なりを許可（トリガーイベント用）
    PUSH        // 押し出し
}
