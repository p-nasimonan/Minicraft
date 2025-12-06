package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.entity.component.BoxCollider;
import uk.youkan.minicraft.entity.component.BoxRenderer;
import uk.youkan.minicraft.entity.component.CollisionLayer;
import uk.youkan.minicraft.entity.component.Physics;
import uk.youkan.minicraft.entity.component.Transform;
import uk.youkan.minicraft.world.World;
import uk.youkan.minicraft.world.block.Block;

/**
 * Entityインターフェースの共通実装を提供する抽象クラス（コンポジションベース）
 * 
 * 各エンティティは以下のコンポーネントを持ちます：
 * - Transform: 位置・サイズ管理
 * - BoxCollider: 当たり判定
 * - BoxRenderer: 描画
 * - Physics: 物理演算（オプション）
 */
public abstract class AbstractEntity implements Entity {
    protected String name;
    protected String id;
    
    // コンポーネント
    protected final Transform transform;
    protected final BoxCollider boxCollider;
    protected final BoxRenderer renderer;
    protected Physics physics;
    
    // 参照
    protected World world;
    
    // レガシーフィールド（コンポーネントへのデリゲート用）
    protected boolean onGround;
    protected float vy;
    protected boolean showInfo;

    public AbstractEntity(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        this.world = world;
        this.name = name;
        this.id = id;
        this.onGround = false;
        this.vy = 0.0f;
        this.showInfo = false;
        
        // コンポーネントを初期化（全ての座標情報はTransformが管理）
        this.transform = new Transform(x, y, z, width, height, depth);
        this.boxCollider = new BoxCollider(transform);
        this.renderer = new BoxRenderer(transform);
        
        // 物理演算はデフォルトで有効
        this.physics = new Physics(transform);
        if (world != null) {
            physics.setGravity(world.getG());
        }
    }

    // === Entity interface implementation ===

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
    public float getX() { return transform.getX(); }
    
    @Override
    public float getY() { return transform.getY(); }
    
    @Override
    public float getZ() { return transform.getZ(); }

    @Override
    public BoxCollider getBoxCollider() {
        if (boxCollider == null) {
            throw new IllegalStateException("BoxCollider is not initialized");
        }
        return boxCollider;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        transform.setPosition(x, y, z);
    }

    public void setName(String name) {
        if (name.length() < 2 || name.length() > 16) {
            throw new IllegalArgumentException("名前は1文字以上16文字未満で設定してください");
        }
        this.name = name;
    }

    // === Collision checking ===

    @Override
    public boolean checkCollisionWithBlocks(float newX, float newY, float newZ) {
        if (world == null) return false;

        int blockX = world.toBlockX(newX);
        int blockY = world.toBlockY(newY);
        int blockZ = world.toBlockZ(newZ);

        if (blockX >= 0 && blockX < world.getWidth() &&
            blockY >= 0 && blockY < world.getHeight() &&
            blockZ >= 0 && blockZ < world.getDepth()) {

            Block targetBlock = world.getBlocks()[blockX][blockY][blockZ];
            if (!targetBlock.isAir()) {
                if (boxCollider.wouldIntersectAt(newX, newY, newZ, targetBlock.getBoxCollider())) {
                    if (newY < transform.getY()) {
                        onGround = true;
                        if (physics != null) {
                            physics.setOnGround(true);
                        }
                    }
                    return true;
                }
            }
        }

        if (newY < transform.getY()) {
            onGround = false;
            if (physics != null) {
                physics.setOnGround(false);
            }
        }
        return false;
    }

    // === Physics ===

    public void applyGravity() {
        if (physics == null) return;

        physics.applyGravity();
        float newY = physics.getNextY();
        vy = physics.getVy();

        if (!checkCollisionWithBlocks(transform.getX(), newY, transform.getZ())) {
            transform.setY(newY);
            onGround = false;
            physics.setOnGround(false);
        } else {
            physics.land();
            vy = 0;
            onGround = true;
        }
    }

    // === Rendering helpers ===

    public void drawFace(String face) {
        renderer.drawFace(face);
    }

    public void drawFace(String face, float[] topColor, float[] bottomColor) {
        renderer.drawFace(face, topColor, bottomColor);
    }

    public void drawEdges() {
        renderer.drawEdges();
    }

    // === Debug ===

    @Override
    public void debugInfo() {
        showInfo = !showInfo;
        if (showInfo) {
            System.out.println("=== " + name + " Debug Info ===");
            System.out.println("  Type: " + id);
            System.out.println("  " + transform);
            if (physics != null) {
                System.out.println("  Physics: vy=" + physics.getVy() + ", onGround=" + physics.isOnGround());
            }
        }
    }

    public String checkCollisionInfo(Entity other) {
        if (boxCollider == null || other.getBoxCollider() == null) {
            return "Collider not initialized";
        }
        
        boolean isColliding = boxCollider.intersects(other.getBoxCollider());
        return String.format("Collision between %s and %s: %s",
            this.name, other.getName(), isColliding ? "YES" : "NO");
    }

    // === Component getters ===

    public Transform getTransform() { return transform; }
    public BoxRenderer getRenderer() { return renderer; }
    public Physics getPhysics() { return physics; }
}
