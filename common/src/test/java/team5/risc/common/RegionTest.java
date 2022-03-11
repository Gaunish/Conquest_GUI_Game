package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RegionTest {
  @Test
  public void test_init() {
    Region r = new Region();
    AreaNode a = new AreaNode("a");
    r.set_owner_id(2);
    r.addArea(a);
    assertEquals(r.toString(), "2: [a]");
    r.removeArea(a);
    assertEquals(true, r.noAreaLeft());
    assertThrows(RuntimeException.class, () -> r.set_init_unit(a, 3));
    r.addArea(a);
    r.set_init_unit(a, 5);
    assertEquals("a:(2: 5)\n[]", a.toString());
  }

}
