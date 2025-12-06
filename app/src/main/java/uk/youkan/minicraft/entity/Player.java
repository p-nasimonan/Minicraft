package uk.youkan.minicraft.entity;

import org.lwjgl.glfw.GLFW;
import uk.youkan.minicraft.input.Action;
import uk.youkan.minicraft.input.Camera;
import uk.youkan.minicraft.input.MouseInput;
import uk.youkan.minicraft.item.Item;
import uk.youkan.minicraft.physics.Collider;
import uk.youkan.minicraft.ui.InputHandler;
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
    private final MouseInput mouseInput;
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private String mode;
    private final float eyeY = 1.7f;

    /**
     * プレイヤーを初期化します
     * @param inputHandler 入力ハンドラ
     * @param world Worldへの参照
     * @param x X座標
     * @param y Y座標
     * @param z Z座標
     */
    public Player(InputHandler inputHandler, World world, float x, float y, float z) {
        super(world, "player", "Player", 10, x, y, z, 100, 1, 4, 1);
        this.world = world;
        inventory = new Item[36];
        this.x = x;
        this.y = y;
        this.z = z;

        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.mode = "survival";

        this.collider = new Collider(x, y, z, width, height, depth);

        this.camera = new Camera();
        camera.setPosition(x, y, z);
        this.mouseInput = inputHandler.getMouseInput();
        this.action = new Action(this, world);
    }

    @Override
    public void update() {
        mouseInput.input();

        float deltaYaw = mouseInput.getDisplVec().x * MOUSE_SENSITIVITY;
        float deltaPitch = mouseInput.getDisplVec().y * MOUSE_SENSITIVITY;

        rotate(deltaPitch, deltaYaw);
        applyGravity();
        updateCamera();
    }

    /**
     * プレイヤーの入力を処理します
     * @param inputHandler 入力ハンドラ
     */
    public void handleInput(InputHandler inputHandler) {
        if (inputHandler.isKeyPressed()) {
            switch (inputHandler.getPressedKey()) {
                case GLFW.GLFW_KEY_W -> this.action.move("forward", yaw);
                case GLFW.GLFW_KEY_S -> this.action.move("backward", yaw);
                case GLFW.GLFW_KEY_A -> this.action.move("left", yaw);
                case GLFW.GLFW_KEY_D -> this.action.move("right", yaw);
                case GLFW.GLFW_KEY_SPACE -> this.action.jump();
                case GLFW.GLFW_KEY_LEFT_SHIFT -> this.action.sneak();
                case GLFW.GLFW_KEY_E -> openInventory();
                case GLFW.GLFW_KEY_F3 -> debugInfo();
            }
        }
        if (inputHandler.isRightButtonPressed()) {
            this.action.replaceBlockInDirection(pitch, yaw, "stone");
        }
        if (inputHandler.isLeftButtonPressed()) {
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
        glTranslatef(-x, -(y + eyeY), -z);
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
        camera.setPosition(x, y, z);
        camera.setRotation(pitch, yaw);
    }

    @Override
    public void debugInfo() {
        super.debugInfo();
        System.out.println("Player Specific Info:");
        System.out.println("  Mode: " + mode);
        System.out.println("  OnGround: " + onGround);
        System.out.println("  Vertical Velocity: " + vy);
        System.out.println("  Camera Rotation: (pitch: " + pitch + ", yaw: " + yaw + ")");
    }

    public void openInventory() {
        // インベントリを開くロジック
    }
}
