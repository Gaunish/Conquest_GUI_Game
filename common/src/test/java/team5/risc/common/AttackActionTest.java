package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackActionTest {
  @Test
  public void test_init() {
    AttackAction m = new AttackAction(0, "area0", "area1", 10);
    assertEquals("area0->area1:10(0)", m.toString());

  }

}
