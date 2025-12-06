package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.physics.Collider;
import uk.youkan.minicraft.world.World;
import uk.youkan.minicraft.world.block.Block;

import static org.lwjgl.opengl.GL11.*;

/**
 * Entityインターフェースの共通実装を提供する抽象クラス
 * 継承ではなくコンポジションを推奨しますが、共通処理をまとめるために使用
 */
public abstract class AbstractEntity implements Entity {
    protected String name;
    protected String id;
    protected float x, y, z;
    protected final float width, height, depth;
    protected Collider collider;
    protected World world;
    protected boolean onGround;
    protected float vy;
    protected boolean showInfo;
    private static final float TERMINAL_VELOCITY = -0.5f;
    private float[][] vertices;

    public AbstractEntity(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
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
        initVertices();
    }

    private void initVertices() {
        this.vertices = new float[][] {
            {x, y, z},
            {x + width, y, z},
            {x + width, y, z + depth},
            {x, y, z + depth},
            {x, y + height, z},
            {x + width, y + height, z},
            {x + width, y + height, z + depth},
            {x, y + height, z + depth},
        };
    }

    // Entity interface implementation
    @Override
    public String getName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Name is not initialized");
        }
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public float getX() { return x; }
    @Override
    public float getY() { return y; }
    @Override
    public float getZ() { return z; }

    @Override
    public Collider getCollider() {
        if (collider == null) {
            throw new IllegalStateException("Collider is not initialized");
        }
        return collider;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (collider != null) {
            collider.setPosition(x, y, z);
        }
        initVertices();
    }

    public void setName(String name) {
        if (name.length() < 2 || name.length() > 16) {
            throw new IllegalArgumentException("名前は1文字以上16文字未満で設定してください");
        }
        this.name = name;
    }

    // 描画ヘルパーメソッド
    public void drawFace(String face) {
        int[] indices = getFaceIndices(face);
        glVertex3f(vertices[indices[0]][0], vertices[indices[0]][1], vertices[indices[0]][2]);
        glVertex3f(vertices[indices[1]][0], vertices[indices[1]][1], vertices[indices[1]][2]);
        glVertex3f(vertices[indices[2]][0], vertices[indices[2]][1], vertices[indices[2]][2]);
        glVertex3f(vertices[indices[3]][0], vertices[indices[3]][1], vertices[indices[3]][2]);
    }

    public void drawFace(String face, float[] topColor, float[] bottomColor) {
        int[] index = getFaceIndices(face);
        glColor3f(bottomColor[0], bottomColor[1], bottomColor[2]);
        glVertex3f(vertices[index[0]][0], vertices[index[0]][1], vertices[index[0]][2]);
        glVertex3f(vertices[index[1]][0], vertices[index[1]][1], vertices[index[1]][2]);
        glColor3f(topColor[0], topColor[1], topColor[2]);
        glVertex3f(vertices[index[2]][0], vertices[index[2]][1], vertices[index[2]][2]);
        glVertex3f(vertices[index[3]][0], vertices[index[3]][1], vertices[index[3]][2]);
    }

    private int[] getFaceIndices(String face) {
        return switch (face) {
            case "front" -> new int[]{0, 1, 5, 4};
            case "back" -> new int[]{3, 2, 6, 7};
            case "left" -> new int[]{0, 3, 7, 4};
            case "right" -> new int[]{1, 2, 6, 5};
            case "top" -> new int[]{4, 5, 6, 7};
            case "bottom" -> new int[]{0, 1, 2, 3};
            default -> throw new IllegalArgumentException("Invalid face: " + face);
        };
    }

    public void drawEdges() {
        glColor3f(0.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        drawLine(vertices[0], vertices[1]);
        drawLine(vertices[1], vertices[5]);
        drawLine(vertices[5], vertices[4]);
        drawLine(vertices[4], vertices[0]);
        drawLine(vertices[0], vertices[3]);
        drawLine(vertices[7], vertices[4]);
        drawLine(vertices[3], vertices[2]);
        drawLine(vertices[2], vertices[6]);
        drawLine(vertices[6], vertices[7]);
        drawLine(vertices[7], vertices[3]);
        drawLine(vertices[1], vertices[2]);
        drawLine(vertices[6], vertices[5]);
        glEnd();
    }

    private void drawLine(float[] start, float[] end) {
        glVertex3f(start[0], start[1], start[2]);
        glVertex3f(end[0], end[1], end[2]);
    }

    // 物理処理
    protected void setCollider(Collider collider) {
        this.collider = collider;
    }

    @Override
    public boolean checkCollisionWithBlocks(float newX, float newY, float newZ) {
        collider.setPosition(newX, newY, newZ);
        boolean collision = false;

        int blockX = world.toBlockX(newX);
        int blockY = world.toBlockY(newY);
        int blockZ = world.toBlockZ(newZ);

        if (blockX >= 0 && blockX < world.getWidth() &&
            blockY >= 0 && blockY < world.getHeight() &&
            blockZ >= 0 && blockZ < world.getDepth()) {

            Block targetBlock = world.getBlocks()[blockX][blockY][blockZ];
            if (collider.intersects(targetBlock.getCollider())) {
                if (!targetBlock.isAir()) {
                    collision = true;
                    if (newY < y) {
                        onGround = true;
                    }
                }
            }
        }

        if (!collision && newY < y) {
            onGround = false;
        }

        collider.setPosition(x, y, z);
        return collision;
    }

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
        } else {
            vy += world.getG();
            float newY = y + vy;
            if (!checkCollisionWithBlocks(x, newY, z)) {
                onGround = false;
            }
        }
    }

    @Override
    public void debugInfo() {
        showInfo = !showInfo;
        if (showInfo) {
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

    public String checkCollisionInfo(Entity other) {
        if (collider == null || other.getCollider() == null) {
            return "Collider not initialized";
        }
        boolean isColliding = collider.intersects(other.getCollider());
        return String.format("Collision between %s and %s: %s",
            this.name, other.getName(), isColliding ? "YES" : "NO");
    }
}
