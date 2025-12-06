package uk.youkan.minicraft.item;

import uk.youkan.minicraft.entity.AbstractEntity;
import uk.youkan.minicraft.physics.Collider;
import uk.youkan.minicraft.world.World;

/**
 * アイテムクラス
 */
public class Item extends AbstractEntity {

    public Item(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        this.collider = new Collider(x, y, z, width, height, depth);
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
