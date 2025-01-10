package jp.ac.uryukyu.ie.e245719;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ButtonTest extends TestBase {

    @Test
    void ボタンが正しく初期化される() {
        Button button = new Button(100, 100, 200, 50, "Start");
        assertEquals("Start", button.getLabel());
    }

    @Test
    void ホバー状態が正しく切り替わる() {
        Button button = new Button(100, 100, 200, 50, "Start");
        button.setHovered(true);
        assertTrue(button.isHovered());
    }

    @Test
    void クリック判定が正しく機能する() {
        Button button = new Button(100, 100, 200, 50, "Start");
        assertTrue(button.isTouched(150, 125));  // ボタンの中心付近
        assertFalse(button.isTouched(0, 0));    // ボタンの外
    }
}