package uk.youkan.minicraft.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import uk.youkan.minicraft.TestBase;

class InputHandlerTest extends TestBase {

    @Test
    void インターフェースが正しく初期化される() {
        System.out.println("=== インターフェース初期化テスト開始 ===");
        InputHandler inputHandler = new InputHandler(dummyWindow);
        System.out.println("インターフェースインスタンスを作成");
        System.out.println("  ウィンドウハンドル: " + dummyWindow);
        assertNotNull(inputHandler);
        System.out.println("=== インターフェース初期化テスト完了 ===\n");
    }

    @Test
    void マウス入力が正しく処理される() {
        System.out.println("=== マウス入力テスト開始 ===");
        InputHandler inputHandler = new InputHandler(dummyWindow);
        System.out.println("マウス入力の更新前状態:");
        System.out.println("  マウスボタン押下: " + inputHandler.isMousePressed());
        inputHandler.update();
        System.out.println("マウス入力の更新後状態:");
        System.out.println("  マウスボタン押下: " + inputHandler.isMousePressed());
        assertFalse(inputHandler.isMousePressed());
        System.out.println("=== マウス入力テスト完了 ===\n");
    }

    @Test
    void キーボード入力が正しく処理される() {
        InputHandler inputHandler = new InputHandler(dummyWindow);
        assertFalse(inputHandler.isKeyPressed("enter"));
        assertFalse(inputHandler.isKeyPressed("esc"));
    }
}
