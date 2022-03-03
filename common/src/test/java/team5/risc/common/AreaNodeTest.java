package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AreaNodeTest {
  @Test
  public void test_getName() {
    AreaNode a= new AreaNode("a");
    assertEquals("a", a.getName());
  }

}
