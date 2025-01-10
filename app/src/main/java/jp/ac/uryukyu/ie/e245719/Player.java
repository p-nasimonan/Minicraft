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
    private Camera camera;
    private MouseInput mouseInput;
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVE_SPEED = 0.1f;
    private final long window;
    private String mode;
    private final float eyeY = 1.7f;

    /**
     * プレイヤーを初期化します
     * @param windowHandle ウィンドウハンドル
     * @param world Worldへの参照
     */
    public Player(long windowHandle, World world) {
        super("player", "Player", 10, 0, 0, 0, 100, 1, 2, 1);
        this.window = windowHandle;
        this.world = world;
        inventory = new ArrayList<>();
        // プレイヤーの初期位置を設定
        this.x = 0.0f;
        this.y = 2.0f; 
        this.z = 0.0f;

        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.mode = "survival";
        
        // プレイヤーのColliderを初期化
        this.collider = new Collider(x, y, z, 0.6f, 2.0f, 0.6f);
        
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
        float deltaYaw = mouseInput.getDisplVec().x * MOUSE_SENSITIVITY;  // 左右回転
        float deltaPitch = mouseInput.getDisplVec().y * MOUSE_SENSITIVITY; // 上下回転
        
        // カメラの回転を更新
        rotate(deltaPitch, deltaYaw);

        // 重力の適用
        applyGravity();

        debugInfo();

        updateCamera();
    }

    public void handleInput(int key, int action, float mX, float mY, boolean isMousePressed) {
        if (action == GLFW.GLFW_PRESS) {
            switch (key) {
                case GLFW.GLFW_KEY_W -> move("forward");
                case GLFW.GLFW_KEY_S -> move("backward");
                case GLFW.GLFW_KEY_A -> move("left");
                case GLFW.GLFW_KEY_D -> move("right");
                case GLFW.GLFW_KEY_SPACE -> jump();
                case GLFW.GLFW_KEY_LEFT_SHIFT -> sneak();
            }
        }
        if (isMousePressed) {
            placeBlockInDirection();
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

    public void putBlock(int x, int y, int z) {
        // ブロックを配置するロジックをここに追加
        Block block = new Block("block", "stone", x, y, z, 1, 1, 1);
        world.putBlock(block);
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

    /**
     * カメラのインスタンスを取得します
     * @return カメラオブジェクト
     */
    public Camera getCamera() {
        return camera;
    }


    @Override
    public void jump() {
        switch (this.mode) {
            case "creative" -> {
                move("up");
            }
            default -> {
                if (onGround) {
                    vy = 0.2f;  // ジャンプの初速度
                    onGround = false;
                }
            }
        }
    }

    public void sneak() {
        // しゃがみのロジックをここに追加
    }
    public void collectItem(Item item) {
        inventory.add(item);
    }

    public void collectBlock(Block block) {
        // ブロックを収集するロジックをここに追加
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

    /**
     * プレイヤーを指定された方向に移動させます
     * @param direction 移動方向 ("forward", "backward", "left", "right", "up", "down")
     */
    public void move(String direction) {
        double radYaw = -Math.toRadians(yaw);
        float dx = 0.0f;
        float dy = 0.0f;
        float dz = 0.0f;

        switch (direction) {
            case "forward" -> {
                dx = -(float) Math.sin(radYaw) * MOVE_SPEED;
                dz = -(float) Math.cos(radYaw) * MOVE_SPEED;
            }
            case "backward" -> {
                dx = (float) Math.sin(radYaw) * MOVE_SPEED;
                dz = (float) Math.cos(radYaw) * MOVE_SPEED;
            }
            case "left" -> {
                dx = -(float) Math.cos(radYaw) * MOVE_SPEED;
                dz = (float) Math.sin(radYaw) * MOVE_SPEED;
            }
            case "right" -> {
                dx = (float) Math.cos(radYaw) * MOVE_SPEED;
                dz = -(float) Math.sin(radYaw) * MOVE_SPEED;
            }
            case "up" -> dy = MOVE_SPEED;
            case "down" -> dy = -MOVE_SPEED;
        }

        float newX = this.x + dx;
        float newY = this.y + dy;
        float newZ = this.z + dz;

        if (!checkCollision(newX, newY, newZ)) {
            this.x = newX;
            this.y = newY;
            this.z = newZ;
            collider.setPosition(x, y, z);
            updateCamera();
        }
    }

    /**
     * プレイヤーの向いている方向にブロックを置きます
     */
    public void placeBlockInDirection() {
        // プレイヤーの位置を取得
        float playerX = this.x;
        float playerY = this.y; // プレイヤーの高さを考慮
        float playerZ = this.z;

        // 距離
        int distance = 2;

        // プレイヤーの向きを取得
        double radYaw = Math.toRadians(yaw);
        
        
        // 向いている方向のベクトルを計算
        float forwardX = (float) (Math.sin(radYaw)) * distance; // X成分
        float forwardZ = (float) (-Math.cos(radYaw)) * distance; // Z成分

        // ブロックを置く位置を計算
        int blockX = (int) (playerX + forwardX);
        int blockY = (int) (playerY) - 1; // プレイヤーの高さから１ブロッック下
        int blockZ = (int) (playerZ + forwardZ);

        // ブロックを配置
        putBlock(blockX, blockY, blockZ);
    }

}