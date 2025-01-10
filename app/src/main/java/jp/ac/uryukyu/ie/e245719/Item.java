package jp.ac.uryukyu.ie.e245719;

public class Item extends GameObject {
    public Item(String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(name, id, x, y, z, width, height, depth);
        this.name = name;
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

    @Override
    public boolean checkCollision(float newX, float newY, float newZ) {
        // アイテムの衝突判定（デフォルトでは衝突なし）
        return false;
    }

}
