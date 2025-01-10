package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CameraTest extends TestBase {

    @Test
    void カメラが正しく初期化される() {
        Camera camera = new Camera();
        assertNotNull(camera);
    }

    @Test
    void カメラの移動が正しく機能する() {
        Camera camera = new Camera();
        camera.moveForward(1.0f);
        assertNotNull(camera.getPosition());
        assertTrue(camera.getPosition().z != 0.0f);
    }
}