package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ActionValidatorTest {
    
    @Test
    public void test_getName() {
      AreaNode a = new AreaNode("a", -1);
      assertEquals("a", a.getName());
      assertEquals(-1, a.getOwnerId());
    }
}
