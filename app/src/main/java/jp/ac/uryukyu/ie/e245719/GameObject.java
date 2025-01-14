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

    public GameObject(String name, String id, float x, float y, float z, float width, float height, float depth) {
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
        if (name.length() < 1 || name.length() > 16) {
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

    /**
     * オブジェクトのColliderを設定します
     * @param collider オブジェクトのCollider
     */
    protected void setCollider(Collider collider) {
        this.collider = collider;
    }

    /**
     * 指定された位置での衝突判定を行います
     * @param newX 新しいX座標
     * @param newY 新しいY座標
     * @param newZ 新しいZ座標
     * @return 衝突する場合はtrue
     */
    public boolean checkCollision(float newX, float newY, float newZ) {
        collider.setPosition(newX, newY, newZ);
        boolean collision = false;
        
        for (GameObject gameobject : world.getGameObjects()) {
            if (collider.intersects(gameobject.getCollider())) {
                if (showInfo == true) {
                    System.out.println("Collision detected with block at: (" + 
                        gameobject.x + ", " + gameobject.y + ", " + gameobject.z + ")");
                }
                collision = true;
                // 下方向への移動で衝突した場合、地面に着地したとみなす
                if (newY < y) {
                    onGround = true;
                }
                break;
            }
        }
        
        // 衝突がない場合は空中にいる
        if (!collision && newY < y) {
            onGround = false;
        }
        
        collider.setPosition(x, y, z);
        return collision;
    }

    public void drawFace(String face) {
        switch (face) {
            case "front" -> {
                glVertex3f(x, y, z);
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y + height, z);
                glVertex3f(x, y + height, z);
            }
            case "back" -> {
                glVertex3f(x, y, z + depth);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x, y + height, z + depth);
            }
            case "left" -> {
                glVertex3f(x, y, z);
                glVertex3f(x, y, z + depth);
                glVertex3f(x, y + height, z + depth);
                glVertex3f(x, y + height, z);
            }
            case "right" -> {
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x + width, y + height, z);
            }
            case "top" -> {
                glVertex3f(x, y + height, z);
                glVertex3f(x + width, y + height, z);
                glVertex3f(x + width, y + height, z + depth);
                glVertex3f(x, y + height, z + depth);
            }
            case "bottom" -> {
                glVertex3f(x, y, z);
                glVertex3f(x + width, y, z);
                glVertex3f(x + width, y, z + depth);
                glVertex3f(x, y, z + depth);
            }
        }
    }

    public void drawEdges() {
        glColor3f(0.0f, 0.0f, 0.0f); // 辺の色（黒）
        glBegin(GL_LINES);
        
        // 辺の描画
        // 各辺の始点と終点を定義
        float[][] vertices = {
            {x, y, z}, // 前面の左下
            {x + width, y, z}, // 前面の右下
            {x + width, y, z + depth}, // 背面の右下
            {x, y, z + depth}, // 背面の左下
            {x, y + height, z}, // 前面の左上
            {x + width, y + height, z}, // 前面の右上
            {x + width, y + height, z + depth}, // 背面の右上
            {x, y + height, z + depth}, // 背面の左上
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
            
            if (!checkCollision(x, newY, z)) {
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
            if(!checkCollision(x, newY, z)){
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
