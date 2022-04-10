package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

public class UpgradeActionTest {
  @Test
  public void test_init() {
      UpgradeAction u = new UpgradeAction(0, "area", 0, 10, 5);
      assertEquals("0 area 0 10-> level 5", u.toString());
  }
    
}
