package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.world.World;

/**
 * 敵クラス
 */
public class Enemy extends Mob {
    public Enemy(World world, String name, String id, int attack, int x, int y, int z, int hp, int width, int height, int depth) {
        super(world, name, id, attack, x, y, z, hp, width, height, depth);
    }

    @Override
    public void update() {
        patrol();
    }

    @Override
    public void render() {
        // 敵のレンダリングロジック
    }

    public void patrol() {
        // パトロールのロジック
    }
}
