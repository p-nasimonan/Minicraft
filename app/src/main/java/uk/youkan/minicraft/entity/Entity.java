package uk.youkan.minicraft.entity;

/**
 * ゲーム内のすべてのエンティティが実装するインターフェース
 * Renderable, Updatable, Collidableを統合
 */
public interface Entity extends Renderable, Updatable, Collidable {
    /**
     * エンティティの名前を取得します
     * @return 名前
     */
    String getName();
    
    /**
     * エンティティのIDを取得します
     * @return ID
     */
    String getId();
    
    /**
     * X座標を取得します
     * @return X座標
     */
    float getX();
    
    /**
     * Y座標を取得します
     * @return Y座標
     */
    float getY();
    
    /**
     * Z座標を取得します
     * @return Z座標
     */
    float getZ();
    
    /**
     * 位置を設定します
     * @param x X座標
     * @param y Y座標
     * @param z Z座標
     */
    void setPosition(float x, float y, float z);
    
    /**
     * デバッグ情報を出力します
     */
    void debugInfo();
}
