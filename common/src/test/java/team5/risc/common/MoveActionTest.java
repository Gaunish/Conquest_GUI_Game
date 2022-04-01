package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveActionTest {
  @Test
  public void test_init() {
    MoveAction m = new MoveAction(0, "area0", "area1", 10);
    assertEquals("area0->area1:10(0)", m.toString());
  }

}
