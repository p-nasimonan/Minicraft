package jp.ac.uryukyu.ie.e245719;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class playerTest {
    @Test
    public void testInit() {
        World world = new World();
        Game game = new Game();
        long mainWindow = game.createWindow();
        Player testObject = new Player(mainWindow, world);  // windowHandleとworldを仮の値で設定
        
        Assertions.assertThat(testObject)
            .satisfies(player -> {
                Assertions.assertThat(player.getName()).isEqualTo("player");
                Assertions.assertThat(player.getId()).isEqualTo("Player");
                Assertions.assertThat(player.getX()).isEqualTo(0.0f);
                Assertions.assertThat(player.getY()).isEqualTo(2.0f);
                Assertions.assertThat(player.getZ()).isEqualTo(0.0f);
            });
    }

    @Test
    public void testGetCollider() {
        World world = new World();
        Game game = new Game();
        long mainWindow = game.createWindow();
        Player testObject = new Player(mainWindow, world);  // windowHandleとworldを仮の値で設定
        Collider collider = testObject.getCollider();
        
        Assertions.assertThat(collider)
            .satisfies(c -> {
                Assertions.assertThat(c.getX()).isEqualTo(0.0f);
                Assertions.assertThat(c.getY()).isEqualTo(2.0f);
                Assertions.assertThat(c.getZ()).isEqualTo(0.0f);
                Assertions.assertThat(c.getWidth()).isEqualTo(0.6f);
                Assertions.assertThat(c.getHeight()).isEqualTo(2.0f);
                Assertions.assertThat(c.getDepth()).isEqualTo(0.6f);
            });
    }
}
