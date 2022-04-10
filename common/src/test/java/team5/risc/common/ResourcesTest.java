package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

public class ResourcesTest {
  @Test
  public void test_init() {
      Resources r = new Resources();
      LinkedHashSet<AreaNode> areas = new LinkedHashSet<>();
      areas.add(new AreaNode("area0", 10, 10, 1));
      assertEquals(100, r.getFood());
      assertEquals(100, r.getTech());

      r.incFoodTech(areas);
      assertEquals(110, r.getFood());
      assertEquals(110, r.getTech());
      assertEquals(false, r.subFood(1000));
      assertEquals(false, r.subTech(1000));
      assertEquals(true, r.subFood(110));
      assertEquals(true, r.subTech(110));
  }    
}
