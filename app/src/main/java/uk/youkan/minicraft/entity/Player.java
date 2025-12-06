package uk.youkan.minicraft.entity;

import uk.youkan.minicraft.input.Action;
import uk.youkan.minicraft.input.Camera;
import uk.youkan.minicraft.input.InputManager;
import uk.youkan.minicraft.item.Item;
import uk.youkan.minicraft.world.World;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * プレイヤークラス
 * プレイヤーの位置、カメラ制御、インベントリ管理などの機能を提供します
 */
public class Player extends Mob {
    private Item[] inventory;
    private float pitch, yaw;
    private final Camera camera;
    private final InputManager inputManager;
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private String mode;
    private final float eyeY = 1.7f;

    /**
     * プレイヤーを初期化します
     * @param inputManager 入力マネージャ
     * @param world Worldへの参照
     * @param x X座標
     * @param y Y座標
     * @param z Z座標
     */
    public Player(InputManager inputManager, World world, float x, float y, float z) {
        super(world, "player", "Player", 10, x, y, z, 100, 1, 4, 1);
        this.world = world;
        inventory = new Item[36];

        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.mode = "survival";

        this.camera = new Camera();
        camera.setPosition(getX(), getY(), getZ());
        this.inputManager = inputManager;
        this.action = new Action(this, world);
    }

    @Override
    public void update() {
        float deltaYaw = inputManager.getMouse().getDisplVec().x * MOUSE_SENSITIVITY;
        float deltaPitch = inputManager.getMouse().getDisplVec().y * MOUSE_SENSITIVITY;

        rotate(deltaPitch, deltaYaw);
        applyGravity();
        updateCamera();
    }

    /**
     * プレイヤーの入力を処理します
     */
    public void handleInput() {
        if (inputManager.isKeyPressed("w")) {
            this.action.move("forward", yaw);
        }
        if (inputManager.isKeyPressed("s")) {
            this.action.move("backward", yaw);
        }
        if (inputManager.isKeyPressed("a")) {
            this.action.move("left", yaw);
        }
        if (inputManager.isKeyPressed("d")) {
            this.action.move("right", yaw);
        }
        if (inputManager.isKeyPressed("space")) {
            this.action.jump();
        }
        if (inputManager.isKeyPressed("shift")) {
            this.action.sneak();
        }
        if (inputManager.isKeyPressed("e")) {
            openInventory();
        }
        if (inputManager.isKeyPressed("f3")) {
            debugInfo();
        }
        
        if (inputManager.isRightButtonPressed()) {
            this.action.replaceBlockInDirection(pitch, yaw, "stone");
        }
        if (inputManager.isLeftButtonPressed()) {
            this.action.replaceBlockInDirection(pitch, yaw, "air");
        }
    }

    @Override
    public void render() {
        // プレイヤーのレンダリングロジック
    }

    public void setCamera() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(-getX(), -(getY() + eyeY), -getZ());
    }

    /**
     * プレイヤーの回転を更新します
     * @param deltaPitch ピッチ角度の変化量
     * @param deltaYaw ヨー角度の変化量
     */
    public void rotate(float deltaPitch, float deltaYaw) {
        this.pitch += deltaPitch;
        this.yaw += deltaYaw;

        if (this.pitch > 90.0f) {
            this.pitch = 90.0f;
        } else if (this.pitch < -90.0f) {
            this.pitch = -90.0f;
        }

        while (this.yaw >= 360.0f) {
            this.yaw -= 360.0f;
        }
        while (this.yaw < 0.0f) {
            this.yaw += 360.0f;
        }
    }

    private void updateCamera() {
        camera.setPosition(getX(), getY(), getZ());
        camera.setRotation(pitch, yaw);
    }

    @Override
    public void debugInfo() {
        super.debugInfo();
        System.out.println("Player Specific Info:");
        System.out.println("  Mode: " + mode);
        System.out.println("  OnGround: " + isOnGround());
        System.out.println("  Vertical Velocity: " + getVy());
        System.out.println("  Camera Rotation: (pitch: " + pitch + ", yaw: " + yaw + ")");
    }

    public void openInventory() {
        // インベントリを開くロジック
    }
}
