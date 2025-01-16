package jp.ac.uryukyu.ie.e245719;

public class Item extends GameObject {

    public Item(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        this.id = id;
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
