package uk.youkan.minicraft.world;

import uk.youkan.minicraft.entity.AbstractEntity;
import uk.youkan.minicraft.entity.component.CollisionLayer;

import static org.lwjgl.opengl.GL11.*;

/**
 * 空クラス
 * 衝突レイヤー: NONE（衝突判定なし）
 */
public class Sky extends AbstractEntity {
    public Sky(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        // 空は衝突判定を持たない
        this.boxCollider.setLayer(CollisionLayer.NONE);
    }

    @Override
    public void update() {
        // 空の更新処理
    }

    @Override
    public void render() {
        glPushMatrix();
        glTranslatef(x, y, z);

        // グラデーションの色を設定
        float[] topColor = {0.3f, 0.8f, 1.0f};
        float[] bottomColor = {0.7f, 0.9f, 1.0f};

        // 空の描画処理
        glBegin(GL_QUADS);
        drawFace("front", topColor, bottomColor);
        drawFace("back", topColor, bottomColor);
        drawFace("left", topColor, bottomColor);
        drawFace("right", topColor, bottomColor);
        drawFace("top", topColor, topColor);
        drawFace("bottom", bottomColor, bottomColor);
        glEnd();

        glPopMatrix();
    }
}
