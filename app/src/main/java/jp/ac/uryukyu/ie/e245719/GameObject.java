package jp.ac.uryukyu.ie.e245719;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public abstract class GameObject {
    private String name;
    protected String id;
    protected float x, y, z;
    protected final float width, height, depth;
    public Collider collider;
    protected World world;
    protected boolean onGround;
    protected float vy;
    protected boolean showInfo;
    private static final float TERMINAL_VELOCITY = -0.5f;
    private float[][] vertices;
    

    public GameObject(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.onGround = false;
        this.vy = 0.0f;
        this.showInfo = false;
        this.collider = new Collider(x, y, z, width, height, depth);
        this.world = world;
        // 頂点座標を設定
        this.vertices = new float[][] {
            {x, y, z}, // 前面の左下
            {x + width, y, z}, // 前面の右下
            {x + width, y, z + depth}, // 背面の右下
            {x, y, z + depth}, // 背面の左下
            {x, y + height, z}, // 前面の左上
            {x + width, y + height, z}, // 前面の右上
            {x + width, y + height, z + depth}, // 背面の右上
            {x, y + height, z + depth}, // 背面の左上
        };
    }

    public abstract void update();
    public abstract void render();
    
    //アクセサ-----------------------------------------------------
    // Getters
    public String getName() {
        if (name.length() < 1) {
            throw new IllegalStateException("Name is not initialized");
        } 
        return name;
    }
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

    
    //Setters
    public void setName(String name) {
        if (name.length() < 2 || name.length() > 16) {
            throw new IllegalArgumentException("名前は1文字以上16時未満で設定してください");
        }
        this.name = name;
    }

    // 位置の更新は一括で行う
    protected void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (collider != null) {
            collider.setPosition(x, y, z);
        }
    }

    //描画-----------------------------------------------------

    /**
     * 指定された面を描画します
     * @param face 描画する面"front", "back", "left", "right", "top", "bottom"
     */
    public void drawFace(String face) {
        int[] indices = getFaceIndices(face);
        glVertex3f(vertices[indices[0]][0], vertices[indices[0]][1], vertices[indices[0]][2]);
        glVertex3f(vertices[indices[1]][0], vertices[indices[1]][1], vertices[indices[1]][2]);
        glVertex3f(vertices[indices[2]][0], vertices[indices[2]][1], vertices[indices[2]][2]);
        glVertex3f(vertices[indices[3]][0], vertices[indices[3]][1], vertices[indices[3]][2]);
    }

    /**
     * 指定された面を描画します
     * @param face 描画する面 "front", "back", "left", "right", "top", "bottom"
     * @param topColor 面の上部の色(r,g,b)
     * @param bottomColor 面の下部の色(r,g,b)
     */
    public void drawFace(String face, float[] topColor, float[] bottomColor) {
        int[] index = getFaceIndices(face);
        glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        glVertex3f(vertices[index[0]][0], vertices[index[0]][1], vertices[index[0]][2]);
        glVertex3f(vertices[index[1]][0], vertices[index[1]][1], vertices[index[1]][2]);
        glColor3f(topColor[0], topColor[1], topColor[2]);
        glVertex3f(vertices[index[2]][0], vertices[index[2]][1], vertices[index[2]][2]);
        glVertex3f(vertices[index[3]][0], vertices[index[3]][1], vertices[index[3]][2]);
    }

    /**
     * 指定された面の頂点インデックスを取得します
     * ここで取得したインデックスは頂点座標の配列verticesに対応しています
     * @param face 面の名前 "front", "back", "left", "right", "top", "bottom"
     * @return 面の頂点インデックス
     */
    private int[] getFaceIndices(String face) {
        return switch (face) {
            case "front" -> new int[]{0, 1, 5, 4}; // 前左下、前右下、前右上、前左上
            case "back" -> new int[]{3, 2, 6, 7}; // 後左下、後右下、後右上、後左上
            case "left" -> new int[]{0, 3, 7, 4}; // 後左下、前左下、前左上、後左上
            case "right" -> new int[]{1, 2, 6, 5}; // 後右下、前右下、前右上、後右上
            case "top" -> new int[]{4, 5, 6, 7}; // 前左上、前右上、後右上、後左上
            case "bottom" -> new int[]{0, 1, 2, 3}; // 前左下、前右下、後右下、後左下
            default -> throw new IllegalArgumentException("Invalid face: " + face);
        };
    }

    /**
     * オブジェクトの辺を描画します
     */
    public void drawEdges() {
        glColor3f(0.0f, 0.0f, 0.0f); // 辺の色（黒）
        glBegin(GL_LINES);
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

    /**
     * 2点間に線を描画します
     * @param start 線の始点
     * @param end 線の終点
     */
    private void drawLine(float[] start, float[] end) {
        glVertex3f(start[0], start[1], start[2]);
        glVertex3f(end[0], end[1], end[2]);
    }


    //物理-----------------------------------------------------


    /**
     * オブジェクトのColliderを設定します
     * @param collider オブジェクトのCollider
     */
    protected void setCollider(Collider collider) {
        this.collider = collider;
    }
    /**
     * 指定された位置でのブロックとの衝突判定を行います
     * @param newX 新しいX座標
     * @param newY 新しいY座標
     * @param newZ 新しいZ座標
     * @return 衝突する場合はtrue
     */
    public boolean checkCollisionWithBlocks(float newX, float newY, float newZ) {
        collider.setPosition(newX, newY, newZ);
        boolean collision = false;

        int blockX = world.toBlockX(newX);
        int blockY = world.toBlockY(newY);
        int blockZ = world.toBlockZ(newZ);

        // 範囲チェックを追加
        if (blockX >= 0 && blockX < world.getWidth() &&
            blockY >= 0 && blockY < world.getHeight() &&
            blockZ >= 0 && blockZ < world.getDepth()) {

            Block targetBlock = world.getBlocks()[blockX][blockY][blockZ];
            if (collider.intersects(targetBlock.getCollider())) {
                if (!targetBlock.id.equals("air")) {
                    collision = true;
                    if (newY < y) {
                        onGround = true;
                    }
                }
            }
        }

        // 衝突がない場合は空中にいる
        if (!collision && newY < y) {
            onGround = false;
        }

        collider.setPosition(x, y, z);
        return collision;
    }

    /**
     * 重力を適用します。
     * オブジェクトが地面に接触していない場合、垂直速度を更新し、
     * 新しいY座標を計算して衝突判定を行います。
     * 衝突が発生した場合、垂直速度をリセットし、地面に接触している状態にします。
     */
    public void applyGravity() {
        if (!onGround) {
            vy += world.getG();
            if (vy < TERMINAL_VELOCITY) {
                vy = TERMINAL_VELOCITY;
            }
            
            float newY = y + vy;
            
            if (!checkCollisionWithBlocks(x, newY, z)) {
                y = newY;
                collider.setPosition(x, y, z);
            } else {
                vy = 0;
                onGround = true;
            }
        }
        else {
            vy += world.getG();
            float newY = y + vy;
            if(!checkCollisionWithBlocks(x, newY, z)){
                onGround = false;
            }
        }
    }

    //Debug-----------------------------------------------------
    /**
     * オブジェクトの状態をデバッグ出力します
     */
    public void debugInfo() {
        showInfo = !showInfo;
        if (showInfo == true) {
        
            System.out.println("""
                === %s Debug Info ===
                Type: %s
                Position: (%.2f, %.2f, %.2f)
                Collider: 
                Position: (%.2f, %.2f, %.2f)
                Size: (%.2f, %.2f, %.2f)
                """.formatted(
                    name, id, x, y, z,
                    collider.x, collider.y, collider.z,
                    collider.width, collider.height, collider.depth
                ));
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

}
