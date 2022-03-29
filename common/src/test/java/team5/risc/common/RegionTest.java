package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RegionTest {
  @Test
  public void test_init() {
    Region r = new Region();
    AreaNode a = new AreaNode("a");
    r.setOwnerId(2);
    r.addArea(a);
    assertEquals(r.toString(), "2: [a]");
    r.removeArea(a);
    assertEquals(true, r.noAreaLeft());
    assertThrows(RuntimeException.class, () -> r.setInitUnit(a, 3));
    r.addArea(a);
    r.setInitUnit(a, 5);
    assertEquals("a:(2: 5)\n[]", a.toString());
  }

}
