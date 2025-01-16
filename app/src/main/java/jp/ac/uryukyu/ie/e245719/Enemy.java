package jp.ac.uryukyu.ie.e245719;

public class Enemy extends Mob {
    public Enemy(World world, String name, String id, int attack, int x, int y, int z, int hp, int width, int height, int depth) {
        super(world, name, id, attack, x, y, z, hp, width, height, depth);
    }

    @Override
    public void update() {
        // 敵の更新ロジックをここに追加
        patrol();
    }

    @Override
    public void render() {
        // 敵のレンダリングロジックをここに追加
    }

    public void patrol() {
        // パトロールのロジックをここに追加
    }

}