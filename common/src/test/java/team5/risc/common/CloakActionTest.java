package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CloakActionTest {
    @Test
    public void test_init() {
        CloakAction a = new CloakAction(0, "area0");
        a.turn = 13;
        assertEquals("0: area0", a.toString());
        assertEquals(false, a.hasEnded(16));
        assertEquals(true, a.hasEnded(17));
    }
}
