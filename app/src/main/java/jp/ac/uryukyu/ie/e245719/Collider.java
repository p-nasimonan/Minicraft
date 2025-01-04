package jp.ac.uryukyu.ie.e245719;

public class Collider {
    private int x, y, z;
    private int width, height, depth;

    public Collider(int x, int y, int z, int width, int height, int depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public boolean intersects(Collider other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y &&
               this.z < other.z + other.depth &&
               this.z + this.depth > other.z;
    }
}
