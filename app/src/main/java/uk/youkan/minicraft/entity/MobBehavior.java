package uk.youkan.minicraft.entity;

/**
 * モブ（動く生物）の振る舞いを定義するインターフェース
 */
public interface MobBehavior {
    /**
     * 攻撃力を取得します
     * @return 攻撃力
     */
    int getAttack();
    
    /**
     * HPを取得します
     * @return HP
     */
    int getHp();
    
    /**
     * ダメージを受けます
     * @param damage ダメージ量
     */
    void takeDamage(int damage);
    
    /**
     * 生存しているかどうかを取得します
     * @return 生存している場合はtrue
     */
    boolean isAlive();
}
