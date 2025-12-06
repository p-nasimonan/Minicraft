package uk.youkan.minicraft.entity.component;

import java.util.EnumMap;
import java.util.Map;

/**
 * 衝突マトリクス
 * レイヤー間の衝突応答を定義します
 */
public class CollisionMatrix {
    private static final Map<CollisionLayer, Map<CollisionLayer, CollisionResponse>> matrix = new EnumMap<>(CollisionLayer.class);

    static {
        // デフォルトですべてNONEに初期化
        for (CollisionLayer layer : CollisionLayer.values()) {
            matrix.put(layer, new EnumMap<>(CollisionLayer.class));
            for (CollisionLayer other : CollisionLayer.values()) {
                matrix.get(layer).put(other, CollisionResponse.NONE);
            }
        }

        // MOB vs BLOCK → 移動をブロック
        setResponse(CollisionLayer.MOB, CollisionLayer.BLOCK, CollisionResponse.BLOCK);
        
        // MOB vs MOB → 押し出し
        setResponse(CollisionLayer.MOB, CollisionLayer.MOB, CollisionResponse.PUSH);
        
        // MOB vs ITEM → 重なりを許可（拾うトリガー）
        setResponse(CollisionLayer.MOB, CollisionLayer.ITEM, CollisionResponse.OVERLAP);
        
        // ITEM vs BLOCK → 移動をブロック（アイテムが落ちて止まる）
        setResponse(CollisionLayer.ITEM, CollisionLayer.BLOCK, CollisionResponse.BLOCK);
        
        // ITEM vs ITEM → 何もしない（アイテム同士は重なる）
        setResponse(CollisionLayer.ITEM, CollisionLayer.ITEM, CollisionResponse.NONE);
        
        // PROJECTILE vs BLOCK → ブロック（矢が壁に当たる）
        setResponse(CollisionLayer.PROJECTILE, CollisionLayer.BLOCK, CollisionResponse.BLOCK);
        
        // PROJECTILE vs MOB → 重なり（ダメージトリガー）
        setResponse(CollisionLayer.PROJECTILE, CollisionLayer.MOB, CollisionResponse.OVERLAP);
    }

    /**
     * レイヤー間の衝突応答を設定（双方向）
     */
    public static void setResponse(CollisionLayer layer1, CollisionLayer layer2, CollisionResponse response) {
        matrix.get(layer1).put(layer2, response);
        matrix.get(layer2).put(layer1, response);
    }

    /**
     * 2つのレイヤー間の衝突応答を取得
     */
    public static CollisionResponse getResponse(CollisionLayer layer1, CollisionLayer layer2) {
        return matrix.get(layer1).get(layer2);
    }

    /**
     * 衝突時に移動をブロックするかどうか
     */
    public static boolean shouldBlock(CollisionLayer layer1, CollisionLayer layer2) {
        return getResponse(layer1, layer2) == CollisionResponse.BLOCK;
    }

    /**
     * 衝突時に重なり（トリガー）イベントを発生させるかどうか
     */
    public static boolean shouldTrigger(CollisionLayer layer1, CollisionLayer layer2) {
        return getResponse(layer1, layer2) == CollisionResponse.OVERLAP;
    }

    /**
     * 衝突時に押し出すかどうか
     */
    public static boolean shouldPush(CollisionLayer layer1, CollisionLayer layer2) {
        return getResponse(layer1, layer2) == CollisionResponse.PUSH;
    }
}
