package jp.ac.uryukyu.ie.e245719;

import java.util.ArrayList;
import java.util.List;

public class Player extends Mob {
    private List<Item> inventory;

    public Player() {
        super("player", "Player", 10, 0, 0, 0, 100, 1, 2, 1);
        inventory = new ArrayList<>();
    }

    @Override
    public void update() {
        // プレイヤーの更新ロジックをここに追加
    }

    @Override
    public void render() {
        // プレイヤーのレンダリングロジックをここに追加
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