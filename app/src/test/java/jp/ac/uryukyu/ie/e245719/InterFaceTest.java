package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterFaceTest {

    @Test
    void testInitialization() {
        long dummyWindow = 1; // 仮のウィンドウハンドル
        InterFace interFace = new InterFace(dummyWindow);
        assertNotNull(interFace);
    }

    @Test
    void testMouseInput() {
        long dummyWindow = 1; // 仮のウィンドウハンドル
        InterFace interFace = new InterFace(dummyWindow);
        interFace.update();
        assertFalse(interFace.isMousePressed());
    }
}