package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SpyMoveActionTest {
    @Test
    public void test_init() {
        SpyMoveAction a = new SpyMoveAction(0, "area0", "area1");
        assertEquals("0: area0->area1", a.toString());
    }
}
