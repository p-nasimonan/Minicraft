package jp.ac.uryukyu.ie.e245719;

public abstract class Mob extends GameObject {
    private int attack;
    private int hp;
    public Action action;

    public Mob(String name, String id, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(name, id, x, y, z, width, height, depth);
        this.attack = attack;
        this.hp = hp;
        this.action = new Action(this, world);
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    // Getters
    public int getAttack() { return attack; }
    public int getHp() { return hp; }

}
