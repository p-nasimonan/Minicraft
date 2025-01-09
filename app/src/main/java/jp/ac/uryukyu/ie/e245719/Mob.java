package jp.ac.uryukyu.ie.e245719;

public abstract class Mob extends GameObject {
    protected int attack;
    protected int hp;

    public Mob(String id, String name, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(id, name, x, y, z, width, height, depth);
        this.attack = attack;
        this.hp = hp;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    public void move() {
        // 移動処理
    }

    public void jump() {
        // ジャンプ処理
        if (onGround) {
            vy = 0.2f;  // ジャンプの初速度
            onGround = false;
        }
    }

    public void attack() {
        // 攻撃処理
    }

    public void hit() {
        // 被ダメージ処理
    }


    
}
