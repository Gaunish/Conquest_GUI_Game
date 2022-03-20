package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MetaInfoPlacementTest {
  @Test
  public void test_init() {
    MetaInfoPlacement m = new MetaInfoPlacement(3);
    assertEquals("You have been assigned 3 units by server.\n", m.inform_unit);
    m.placeStr("area1");
    assertEquals("How many units do you want to place in area1?\n", m.place_unit);
  }

}
