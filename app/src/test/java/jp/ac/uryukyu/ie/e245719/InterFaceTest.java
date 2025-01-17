package jp.ac.uryukyu.ie.e245719;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class InterFaceTest extends TestBase {

    @Test
    void インターフェースが正しく初期化される() {
        System.out.println("=== インターフェース初期化テスト開始 ===");
        InterFace interFace = new InterFace(dummyWindow);
        System.out.println("インターフェースインスタンスを作成");
        System.out.println("  ウィンドウハンドル: " + dummyWindow);
        assertNotNull(interFace);
        System.out.println("=== インターフェース初期化テスト完了 ===\n");
    }

    @Test
    void マウス入力が正しく処理される() {
        System.out.println("=== マウス入力テスト開始 ===");
        InterFace interFace = new InterFace(dummyWindow);
        System.out.println("マウス入力の更新前状態:");
        System.out.println("  マウスボタン押下: " + interFace.isMousePressed());
        interFace.update();
        System.out.println("マウス入力の更新後状態:");
        System.out.println("  マウスボタン押下: " + interFace.isMousePressed());
        assertFalse(interFace.isMousePressed());
        System.out.println("=== マウス入力テスト完了 ===\n");
    }

    @Test
    void キーボード入力が正しく処理される() {
        InterFace interFace = new InterFace(dummyWindow);
        assertFalse(interFace.isKeyPressed("enter"));
        assertFalse(interFace.isKeyPressed("esc"));
    }
}