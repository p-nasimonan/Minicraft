package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MouseInputTest {

    @Test
    void testMouseInputInitialization() {
        long dummyWindow = 1; // 仮のウィンドウハンドル
        MouseInput mouseInput = new MouseInput(dummyWindow);
        assertNotNull(mouseInput);
    }

    @Test
    void testMouseInputUpdate() {
        long dummyWindow = 1; // 仮のウィンドウハンドル
        MouseInput mouseInput = new MouseInput(dummyWindow);
        assertDoesNotThrow(mouseInput::input);
    }
}