# マインクラフト風のLWJGLを使ったゲーム
**プログラミングの課題で好きな作品を作るらしいのでJAVAといえばマインクラフトだと思い、作ろうとした**
- オブジェクト指向のいい練習にもなるだろうし...
![image](https://github.com/user-attachments/assets/28732c20-fd68-400e-a928-cc9087b15609)


## クラス図を書いた(mermaid)
```mermaid
classDiagram
    class Game {
        -window: long
        -world: World
        -player: Player
        -gameStarted: boolean
        -startButton: Button
        -cursorEnabled: boolean
        +start()
        -init()
        -loop()
        -update()
        -render()
        -cleanup()
    }

    class GameObject {
        <<abstract>>
        #name: String
        #type: String
        #x: float
        #y: float
        #z: float
        #collider: Collider
        +update()*
        +render()*
        +checkCollision()*
        +debugInfo()
    }

    class World {
        -blocks: List~Block~
        -enemies: List~Enemy~
        -gameObjects: List~GameObject~
        -g: float
        +generate()
        +update()
        +render()
        +gravity()
    }

    class Player {
        -inventory: List~Item~
        -pitch: float
        -yaw: float
        -camera: Camera
        -mouseInput: MouseInput
        +move()
        +rotate()
        +jump()
        +sneak()
        +setCamera()
    }

    class Mob {
        <<abstract>>
        #attack: int
        #hp: int
        +move()
        +jump()
        +attack()
        +hit()
    }

    class Enemy {
        +patrol()
    }

    class Item {
    }

    class Block {
        -type: String
    }

    class Camera {
        -direction: Vector3f
        -position: Vector3f
        -rotation: Vector2f
        +moveForward()
        +moveBackward()
        +moveLeft()
        +moveRight()
    }

    class Button {
        -x: float
        -y: float
        -width: float
        -height: float
        -label: String
        -hovered: boolean
        +render()
        +isTouched()
    }

    class Collider {
        -x: float
        -y: float
        -z: float
        -width: float
        -height: float
        -depth: float
        +intersects()
        +canMove()
    }

    Game --> World
    Game --> Player
    Game --> Button
    World --> GameObject
    GameObject <|-- Mob
    GameObject <|-- Item
    Item <|-- Block
    Mob <|-- Player
    Mob <|-- Enemy
    Player --> Camera
    Player --> MouseInput
    GameObject --> Collider
```

## 試したこと
- ビットマップフォントを使えるようにしようとした
    - 非常にめんどくさかった
    - あきらめた()

## インストールInstallation
リリースノート
