package uk.youkan.minicraft.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.youkan.minicraft.TestBase;

class MouseInputTest extends TestBase {

    @Test
    void マウス入力が正しく初期化される() {
        MouseInput mouseInput = new MouseInput(dummyWindow);
        assertNotNull(mouseInput);
    }

    @Test
    void マウス入力の更新が例外を発生させない() {
        MouseInput mouseInput = new MouseInput(dummyWindow);
        assertDoesNotThrow(mouseInput::input);
    }
}
