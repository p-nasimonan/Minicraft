package jp.ac.uryukyu.ie.e245719;

import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;


/**
 * プレイヤークラス
 * プレイヤーの位置、カメラ制御、インベントリ管理などの機能を提供します
 */
public class Player extends Mob {
    private List<Item> inventory;
    private float x, y, z;
    private float pitch, yaw;
    private Camera camera;
    private MouseInput mouseInput;
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVE_SPEED = 0.1f;
    private final long window;

    /**
     * プレイヤーを初期化します
     * @param windowHandle ウィンドウハンドル
     */
    public Player(long windowHandle) {
        super("player", "Player", 10, 0, 0, 0, 100, 1, 2, 1);
        this.window = windowHandle;
        inventory = new ArrayList<>();
        this.x = 0.0f;
        this.y = 1.8f; // プレイヤーの目の高さ
        this.z = 0.0f;
        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.camera = new Camera();
        this.mouseInput = new MouseInput(windowHandle);
        
        // カメラの初期位置をプレイヤーの位置に設定
        camera.setPosition(x, y, z);
        updateCamera();
    }

    @Override
    public void update() {
        // マウス入力の更新
        mouseInput.input();
        
        // マウスの移動量を取得して回転を更新（X,Y軸を入れ替えて適切な方向に）
        float deltaYaw = -mouseInput.getDisplVec().x * MOUSE_SENSITIVITY;  // 左右回転
        float deltaPitch = -mouseInput.getDisplVec().y * MOUSE_SENSITIVITY; // 上下回転
        
        // カメラの回転を更新
        rotate(deltaPitch, deltaYaw);

        // キーボード入力による移動を処理
        handleMovement();
    }

    /**
     * キーボード入力による移動を処理します
     */
    private void handleMovement() {
        // 移動方向を計算
        double radYaw = Math.toRadians(yaw);
        float dx = 0.0f;
        float dz = 0.0f;

        // 前後移動
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            dx -= (float) Math.sin(radYaw) * MOVE_SPEED;
            dz -= (float) Math.cos(radYaw) * MOVE_SPEED;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            dx += (float) Math.sin(radYaw) * MOVE_SPEED;
            dz += (float) Math.cos(radYaw) * MOVE_SPEED;
        }

        // 左右移動
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            dx -= (float) Math.cos(radYaw) * MOVE_SPEED;
            dz += (float) Math.sin(radYaw) * MOVE_SPEED;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            dx += (float) Math.cos(radYaw) * MOVE_SPEED;
            dz -= (float) Math.sin(radYaw) * MOVE_SPEED;
        }

        // ジャンプとしゃがみ
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
            move(0, MOVE_SPEED, 0);
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {
            move(0, -MOVE_SPEED, 0);
        }

        // 実際の移動を適用
        if (dx != 0 || dz != 0) {
            move(dx, 0, dz);
        }
    }

    @Override
    public void render() {
        // プレイヤーのレンダリングロジックをここに追加
    }

    public void setCamera() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);  // 上下の視点
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);    // 左右の視点
        glTranslatef(-x, -y, -z);             // カメラ位置
    }

    public void move(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        updateCamera();
    }

    public void rotate(float deltaPitch, float deltaYaw) {
        this.pitch += deltaPitch;
        this.yaw += deltaYaw;
        
        // ピッチ角度を制限（-90度から90度まで）
        if (this.pitch > 89.0f) {
            this.pitch = 89.0f;
        } else if (this.pitch < -89.0f) {
            this.pitch = -89.0f;
        }
        
        // ヨー角度を360度以内に正規化
        while (this.yaw >= 360.0f) {
            this.yaw -= 360.0f;
        }
        while (this.yaw < 0.0f) {
            this.yaw += 360.0f;
        }
        
        updateCamera();
    }

    private void updateCamera() {
        camera.setPosition(x, y, z);
        camera.setRotation(pitch, yaw);
    }

    /**
     * カメラのインスタンスを取得します
     * @return カメラオブジェクト
     */
    public Camera getCamera() {
        return camera;
    }

    public void jump() {
        // ジャンプのロジックをここに追加
    }

    public void collectItem(Item item) {
        inventory.add(item);
    }

    public void collectBlock(Block block) {
        // ブロックを収集するロジックをここに追加
    }
}