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

  @Test
  public void test_level() {
    Region r = new Region();
    AreaNode a = new AreaNode("area2", 10, 10, 1);
    r.setOwnerId(2);
    r.addArea(a);
    assertEquals(true, r.checkFoodEnough(100));

    r.incFoodTech();
    assertEquals(true, r.checkFoodEnough(110));
    assertEquals(false, r.checkFoodEnough(111));
    
    r.subFood(1);
    r.subTech(1);
    assertEquals(false, r.checkFoodEnough(110));
    assertEquals(false, r.checkTechEnough(110));
    assertEquals("area2\nOwner: Player2\n"+a.displayRsrc(), r.strDisplay());
    assertEquals("\nFood: 109\nTech: 109\n", r.getInfo());
  }

}
