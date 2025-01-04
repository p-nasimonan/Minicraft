package jp.ac.uryukyu.ie.e245719;

public class Block extends  Item {
    private String type;
    private Collider collider;

    public Block(String type, int x, int y, int z, int width, int height, int depth) {
        this.type = type;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    public void render() {
        // レンダリング処理
    }

    public boolean checkCollision(Collider other) {
        return collider.intersects(other);
    }
}
