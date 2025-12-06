package uk.youkan.minicraft.world.block;

import uk.youkan.minicraft.entity.component.CollisionLayer;
import uk.youkan.minicraft.item.Item;
import uk.youkan.minicraft.world.World;

import static org.lwjgl.opengl.GL11.*;

/**
 * ブロッククラス
 * 衝突レイヤー: BLOCK（Mobは乗れる・通れない）
 */
public class Block extends Item {

    public Block(World world, String name, String id, float x, float y, float z, float width, float height, float depth) {
        super(world, name, id, x, y, z, width, height, depth);
        
        // Blockの衝突レイヤーを設定（Mobの移動をブロック）
        this.boxCollider.setLayer(CollisionLayer.BLOCK);
    }

    public boolean isAir() {
        return this.id.equals("air");
    }

    @Override
    public void render() {
        glEnable(GL_DEPTH_TEST);
        glPushMatrix();
        glTranslatef(getX(), getY(), getZ());

        glCullFace(GL_BACK);

        // ブロックの色を設定
        switch (id) {
            case "grass" -> glColor3f(0.0f, 0.5f, 0.0f);
            case "stone" -> glColor3f(0.3f, 0.3f, 0.3f);
            default -> glColor3f(1.0f, 1.0f, 1.0f);
        }

        // 面を描画(触れていない面のみ)
        glBegin(GL_QUADS);
        if (!isFaceTouching("front")) drawFace("front");
        if (!isFaceTouching("back")) drawFace("back");
        if (!isFaceTouching("left")) drawFace("left");
        if (!isFaceTouching("right")) drawFace("right");
        if (!isFaceTouching("top")) drawFace("top");
        if (!isFaceTouching("bottom")) drawFace("bottom");
        glEnd();

        // 辺を描画
        drawEdges();

        glPopMatrix();
    }

    /**
     * 指定された面が他のブロックと触れているかを判定します
     * @param face 面の名前 ("front", "back", "left", "right", "top", "bottom")
     * @return 触れている場合はtrue
     */
    private boolean isFaceTouching(String face) {
        int x = (int) getX();
        int y = (int) getY();
        int z = (int) getZ();
        return switch (face) {
            case "front" -> !world.getBlockAt(x, y, z - 1).isAir();
            case "back" -> !world.getBlockAt(x, y, z + 1).isAir();
            case "left" -> !world.getBlockAt(x - 1, y, z).isAir();
            case "right" -> !world.getBlockAt(x + 1, y, z).isAir();
            case "top" -> !world.getBlockAt(x, y + 1, z).isAir();
            case "bottom" -> !world.getBlockAt(x, y - 1, z).isAir();
            default -> false;
        };
    }
}
