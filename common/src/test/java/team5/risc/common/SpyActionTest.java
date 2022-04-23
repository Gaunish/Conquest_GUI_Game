package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SpyActionTest {
    @Test
    public void test_init() {
        SpyAction a = new SpyAction(0, "area0", "area1");
        a.turn = 13;
        a.distance = 4;
        assertEquals("0: area0->area1", a.toString());
        assertEquals(false, a.hasReached(17));
        assertEquals(true, a.hasReached(18));
    }
    
}
