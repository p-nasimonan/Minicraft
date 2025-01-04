package jp.ac.uryukyu.ie.e245719;

public abstract class Mob extends GameObject {
    private String id;
    private String name;
    private int attack;
    private int x, y, z;
    private int hp;
    private Collider collider;

    public Mob(String id, String name, int attack, int x, int y, int z, int hp, int width, int height, int depth) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.z = z;
        this.hp = hp;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    public void move() {
        // 移動処理
    }

    public void attack() {
        // 攻撃処理
    }

    public void hit() {
        // 被ダメージ処理
    }

    public boolean checkCollision(Collider other) {
        return collider.intersects(other);
    }
}
