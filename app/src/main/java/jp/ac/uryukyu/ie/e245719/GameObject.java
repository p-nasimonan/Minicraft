package jp.ac.uryukyu.ie.e245719;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public abstract class GameObject {
    protected String name;
    protected String type;
    protected float x;
    protected float y;
    protected float z;
    protected Collider collider;

    public GameObject(String name, String type, float x, float y, float z, float width, float height, float depth) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.collider = new Collider(x, y, z, width, height, depth);
    }

    public abstract void update();
    public abstract void render();

    /**
     * オブジェクトのColliderを取得します
     * @return オブジェクトのCollider
     */
    public Collider getCollider() {
        if (collider == null) {
            throw new IllegalStateException("Collider is not initialized");
        }
        return collider;
    }

    // 既存のgetterメソッド
    public String getName() { return name; }
    public String getType() { return type; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    /**
     * オブジェクトの状態をデバッグ出力します
     */
    public void debugInfo() {
        System.out.println("=== " + name + " Debug Info ===");
        System.out.println("Type: " + type);
        System.out.println("Position: (" + x + ", " + y + ", " + z + ")");
        if (collider != null) {
            System.out.println("Collider: " + 
                "\n  Position: (" + collider.getX() + ", " + collider.getY() + ", " + collider.getZ() + ")" +
                "\n  Size: (" + collider.getWidth() + ", " + collider.getHeight() + ", " + collider.getDepth() + ")");
        }
    }

    /**
     * オブジェクトの衝突状態をチェックします
     * @param other 衝突を確認する対象のオブジェクト
     * @return 衝突情報を含む文字列
     */
    public String checkCollisionInfo(GameObject other) {
        if (collider == null || other.getCollider() == null) {
            return "Collider not initialized";
        }
        boolean isColliding = collider.intersects(other.getCollider());
        return String.format("Collision between %s and %s: %s", 
            this.name, other.name, isColliding ? "YES" : "NO");
    }

    /**
     * 指定された位置での衝突判定を行います
     * @param newX 新しいX座標
     * @param newY 新しいY座標
     * @param newZ 新しいZ座標
     * @return 衝突する場合はtrue
     */
    public abstract boolean checkCollision(float newX, float newY, float newZ);

    public void drawFace(String face) {
        float x = collider.getX();
        float y = collider.getY();
        float z = collider.getZ();
        float width = collider.getWidth();
        float height = collider.getHeight();
        float depth = collider.getDepth();

        switch (face) {
            case "front":
                glVertex3f(x, y, z);
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y + height, z);
                glVertex3f(x, y + height, z);
                break;
            case "back":
                glVertex3f(x, y, z + depth);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x, y + height, z + depth);
                break;
            case "left":
                glVertex3f(x, y, z);
                glVertex3f(x, y, z + depth);
                glVertex3f(x, y + height, z + depth);
                glVertex3f(x, y + height, z);
                break;
            case "right":
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x + width, y + height, z);
                break;
            case "top":
                glVertex3f(x, y + height, z);
                glVertex3f(x + width, y + height, z);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x, y + height, z + depth);
                break;
            case "bottom":
                glVertex3f(x, y, z);
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x, y, z + depth);
                break;
        }
    }

    public void drawEdges() {
        glColor3f(0.0f, 0.0f, 0.0f); // 辺の色（黒）
        glBegin(GL_LINES);
        
        // 辺の描画
        // 各辺の始点と終点を定義
        float[][] vertices = {
            {collider.getX(), collider.getY(), collider.getZ()}, // 前面の左下
            {collider.getX() + collider.getWidth(), collider.getY(), collider.getZ()}, // 前面の右下
            {collider.getX() + collider.getWidth(), collider.getY(), collider.getZ() + collider.getDepth()}, // 背面の右下
            {collider.getX(), collider.getY(), collider.getZ() + collider.getDepth()}, // 背面の左下
            {collider.getX(), collider.getY() + collider.getHeight(), collider.getZ()}, // 前面の左上
            {collider.getX() + collider.getWidth(), collider.getY() + collider.getHeight(), collider.getZ()}, // 前面の右上
            {collider.getX() + collider.getWidth(), collider.getY() + collider.getHeight(), collider.getZ() + collider.getDepth()}, // 背面の右上
            {collider.getX(), collider.getY() + collider.getHeight(), collider.getZ() + collider.getDepth()}, // 背面の左上
        };

        // 各辺を描画
        // 前面
        drawLine(vertices[0], vertices[1]); // 左下から右下
        drawLine(vertices[1], vertices[5]); // 右下から右上
        drawLine(vertices[5], vertices[4]); // 右上から左上
        drawLine(vertices[4], vertices[0]); // 左上から左下

        // 左面
        drawLine(vertices[0], vertices[3]); // 前面の左下から背面の左下
        drawLine(vertices[7], vertices[4]); // 背面の左上から前面の左上

        // 背面
        drawLine(vertices[3], vertices[2]); // 左下から右下
        drawLine(vertices[2], vertices[6]); // 右下から右上
        drawLine(vertices[6], vertices[7]); // 右上から左上
        drawLine(vertices[7], vertices[3]); // 左上から左下


        // 右面
        drawLine(vertices[1], vertices[2]); // 前面の右下から背面の右下
        drawLine(vertices[6], vertices[5]); // 背面の右上から前面の右上

        glEnd();
    }

    private void drawLine(float[] start, float[] end) {
        glVertex3f(start[0], start[1], start[2]);
        glVertex3f(end[0], end[1], end[2]);
    }
}
