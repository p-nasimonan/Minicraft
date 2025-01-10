package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterFaceTest extends TestBase {

    @Test
    void インターフェースが正しく初期化される() {
        InterFace interFace = new InterFace(dummyWindow);
        assertNotNull(interFace);
    }

    @Test
    void マウス入力が正しく処理される() {
        InterFace interFace = new InterFace(dummyWindow);
        interFace.update();
        assertFalse(interFace.isMousePressed());
    }

    @Test
    void キーボード入力が正しく処理される() {
        InterFace interFace = new InterFace(dummyWindow);
        assertFalse(interFace.isKeyPressed("enter"));
        assertFalse(interFace.isKeyPressed("esc"));
    }
}