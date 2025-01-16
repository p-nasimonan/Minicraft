package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;


/**
 * プレイヤークラス
 * プレイヤーの位置、カメラ制御、インベントリ管理などの機能を提供します
 */
public class Player extends Mob {
    private List<Item> inventory;
    private float pitch, yaw;
    private final Camera camera;
    private final MouseInput mouseInput;
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private String mode;
    private final float eyeY = 1.7f;

    /**
     * プレイヤーを初期化します
     * @param windowHandle ウィンドウハンドル
     * @param world Worldへの参照
     */
    public Player(InterFace interFace, World world, float x, float y, float z) {
        super(world, "player", "Player", 10, x, y, z, 100, 1, 4, 1);
        this.world = world;
        inventory = new ArrayList<>();
        // プレイヤーの初期位置を設定
        this.x = x;
        this.y = y; 
        this.z = z;

        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.mode = "survival";
        
        // プレイヤーのColliderを初期化
        this.collider = new Collider(x, y, z, width, height, depth);
        
        this.camera = new Camera();

        
        // カメラの初期位置をプレイヤーの位置に設定
        camera.setPosition(x, y, z);
        // マウス入力の初期化
        this.mouseInput = interFace.getMouseInput();
        // 動作の初期化
        this.action = new Action(this, world);
    }

    @Override
    public void update() {
        // マウス入力の更新
        mouseInput.input();
        
        // マウスの移動量を取得して回転を更新（X,Y軸を入れ替えて適切な方向に）
        float deltaYaw = mouseInput.getDisplVec().x * MOUSE_SENSITIVITY;  // 左右回転
        float deltaPitch = mouseInput.getDisplVec().y * MOUSE_SENSITIVITY; // 上下回転
        
        // カメラの回転を更新
        rotate(deltaPitch, deltaYaw);

        // 重力の適用
        applyGravity();

        updateCamera();
    }

    /**
     * プレイヤーの入力を処理します
     * @param key
     * @param action
     * @param mX
     * @param mY
     * @param isMousePressed
     */
    public void handleInput(InterFace interFace) {
        if (interFace.isKeyPressed()) {
            switch (interFace.getPressedKey()) {
                case GLFW.GLFW_KEY_W -> this.action.move("forward", yaw);
                case GLFW.GLFW_KEY_S -> this.action.move("backward", yaw);
                case GLFW.GLFW_KEY_A -> this.action.move("left", yaw);
                case GLFW.GLFW_KEY_D -> this.action.move("right", yaw);
                case GLFW.GLFW_KEY_SPACE -> this.action.jump();
                case GLFW.GLFW_KEY_LEFT_SHIFT -> this.action.sneak();
                case GLFW.GLFW_KEY_E -> openInventory();
                case GLFW.GLFW_KEY_F3 -> debugInfo(); //トグルにしたい
            }
        }
        if (interFace.isLeftButtonPressed()) {
            this.action.replaceBlockInDirection(pitch, yaw, "stone");
        }
        if (interFace.isRightButtonPressed()) {
            //　 破壊する(空気に置き換える)
            this.action.replaceBlockInDirection(pitch, yaw, "air");
        }
    }



    @Override
    public void render() {
        // プレイヤーのレンダリングロジックをここに追加
    }

    public void setCamera() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);  // 上下の視点
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);    // 左右の視点
        glTranslatef(-x, -(y+eyeY), -z);             // カメラ位置
    }

    /**
     * プレイヤーの回転を更新します
     * @param deltaPitch ピッチ角度の変化量
     * @param deltaYaw ヨー角度の変化量
     */
    public void rotate(float deltaPitch, float deltaYaw) {
        this.pitch += deltaPitch;
        this.yaw += deltaYaw;
        
        // ピッチ角度を制限（-90度から90度まで）
        if (this.pitch > 90.0f) {
            this.pitch = 90.0f;
        } else if (this.pitch < -90.0f) {
            this.pitch = -90.0f;
        }
        
        // ヨー角度を360度以内に正規化
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
        // インベントリを開くロジックをここに追加
    }

}