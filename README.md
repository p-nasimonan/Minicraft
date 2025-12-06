# マインクラフト風のLWJGLを使ったゲーム
**プログラミングの課題で好きな作品を作るらしいのでJAVAといえばマインクラフトだと思い、それっぽいのを作ろうとした**

 オブジェクト指向のいい練習にもなるだろうし...
![Minicraftv1 01](https://github.com/user-attachments/assets/59e83d2a-3233-4a95-b995-a53b3de4eb92)

- 画像：最新版のワールドの画面

## インストールInstallation
### [ここから最新版のjarをダウンロード](https://github.com/p-nasimonan/Minicraft/releases/latest)

### LWJGLの厄介ポイント
- macOSの場合jvmの引数に"-XstartOnFirstThread"をつけないといけない **(逆にwindowsはこの設定は消してください)**
  - VScodeの開発環境で実行する場合.vscode内のjsonファイルを書き換える必要がある
  - launch.json
    ```.vscode/launch.json
    "vmArgs": "-XstartOnFirstThread",
    ```
    setting.json
    ```.vscode/setting.json
        "java.test.config": {
        "vmArgs": ["-XstartOnFirstThread"]
    }
    ```
  を追加したらテストも実行できるようになる

## ゲームの操作方法
1. 再生ボタンのようなボタンが出てくるからそれをクリックする
2. WASDで移動でき、マウス操作で視点操作ができる。
3.  ESCキーでマウスカーソルの表示・非表示を切り替えられる
4.  マウスをクリックすると視点と同じ方向にブロックを置ける

## ソースコードを実行するには
 - java開発環境、Gradleが必要
 - Gradle経由で起動するのが推奨（依存関係やネイティブ設定が自動で処理されます）
 - Mainクラスを実行するとゲーム画面が起動します


### 実行方法（Gradleで実行する方法）

#### 開発中（Gradleで実行）
Gradleの `application` プラグインで立ち上げる方法です。依存関係やネイティブライブラリが正しく組み込まれるため、開発中はこの方法を推奨します。

- macOS / Linux:
```bash
./gradlew :app:run
```

#### 実行用JAR（オプション）
ビルドして配布用のJarを作成して実行する方法です。

ビルド:
```bash
./gradlew :app:fatJar
```

- macOS（ターミナル）:
```bash
java -XstartOnFirstThread -jar app/build/libs/minicraft-1.01.jar
```

Windowsではjarをダブルクリックして実行することもできます。
