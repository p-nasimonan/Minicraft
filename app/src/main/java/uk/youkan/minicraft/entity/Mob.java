package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.entity.component.CollisionLayer;
import uk.youkan.minicraft.input.Action;
import uk.youkan.minicraft.world.World;

/**
 * モブ（動く生物）の基本実装
 * MobBehaviorインターフェースを実装
 * 衝突レイヤー: MOB
 */
public abstract class Mob extends AbstractEntity implements MobBehavior {
    private int attack;
    private int hp;
    private int maxHp;
    public Action action;

    public Mob(World world, String name, String id, int attack, float x, float y, float z, int hp, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        this.attack = attack;
        this.hp = hp;
        this.maxHp = hp;
        this.action = new Action(this, world);
        
        // Mobの衝突レイヤーを設定
        this.boxCollider.setLayer(CollisionLayer.MOB);
    }

    @Override
    public int getAttack() { return attack; }
    
    @Override
    public int getHp() { return hp; }
    
    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    
    @Override
    public boolean isAlive() {
        return hp > 0;
    }
    
    public int getMaxHp() { return maxHp; }
    
    // Action用のアクセサメソッド（Physicsコンポーネントへデリゲート）
    public boolean isOnGround() { 
        return physics != null ? physics.isOnGround() : onGround; 
    }
    
    public void setOnGround(boolean onGround) { 
        this.onGround = onGround;
        if (physics != null) {
            physics.setOnGround(onGround);
        }
    }
    
    public float getVy() { 
        return physics != null ? physics.getVy() : vy; 
    }
    
    public void setVy(float vy) { 
        this.vy = vy;
        if (physics != null) {
            physics.setVy(vy);
        }
    }
}
