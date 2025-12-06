package uk.youkan.minicraft.ui;

import uk.youkan.minicraft.input.InputManager;
import java.util.ArrayList;
import java.util.List;

/**
 * HUD（Heads-Up Display）
 * ゲーム画面上のUI要素（ボタン、テキストなど）を一元管理します
 */
public class HUD {
    private final List<Button> buttons;
    private InputManager inputManager;
    private boolean visible;

    public HUD() {
        this.buttons = new ArrayList<>();
        this.visible = true;
    }

    /**
     * InputManagerを設定
     */
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * ボタンを追加
     */
    public void addButton(Button button) {
        buttons.add(button);
    }

    /**
     * HUDを更新（入力処理）
     */
    public void update() {
        if (!visible || inputManager == null) return;

        for (Button button : buttons) {
            button.update(inputManager);
        }
    }

    /**
     * HUDを描画
     */
    public void render() {
        if (!visible) return;

        for (Button button : buttons) {
            button.render();
        }
    }

    /**
     * HUDの表示/非表示を切り替え
     */
    public void toggleVisibility() {
        this.visible = !visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    /**
     * 指定されたラベルのボタンを取得
     */
    public Button getButton(String label) {
        return buttons.stream()
            .filter(b -> b.getLabel().equals(label))
            .findFirst()
            .orElse(null);
    }

    /**
     * HUDをリセット（ボタン削除）
     */
    public void clear() {
        buttons.clear();
    }
}
