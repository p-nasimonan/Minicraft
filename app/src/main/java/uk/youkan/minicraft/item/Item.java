package uk.youkan.minicraft.item;

import uk.youkan.minicraft.entity.AbstractEntity;
import uk.youkan.minicraft.entity.component.CollisionLayer;
import uk.youkan.minicraft.world.World;

/**
 * アイテムクラス
 * 衝突レイヤー: ITEM（Mobは通過可能、拾えるトリガー）
 */
public class Item extends AbstractEntity {

    public Item(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        
        // Itemの衝突レイヤーを設定（Mobは通過可能だがトリガーイベントを発生）
        this.boxCollider.setLayer(CollisionLayer.ITEM);
    }

    @Override
    public void update() {
        // アイテムの更新処理
    }

    @Override
    public void render() {
        // アイテムの描画処理
    }
}
