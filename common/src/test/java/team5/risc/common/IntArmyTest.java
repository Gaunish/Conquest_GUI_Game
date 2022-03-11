package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class IntArmyTest {
  @Test
  public void test_init() {
    IntArmy a = new IntArmy(0, 1);
    assertEquals(a.getOwnerId(), 0);
    assertEquals(a.getUnitNum(), 1);
    a.removeUnit(1);
    assertEquals(a.getUnitNum(), 0);
    a.removeUnit(1);
    assertEquals(a.getUnitNum(), 0);
    a.addUnit(5);
    a.removeUnit(3);
    assertEquals(a.getUnitNum(), 2);

  }

  @Test
  public void test_merge() {
    IntArmy a = new IntArmy(0, 3);
    IntArmy b = new IntArmy(0, 3);
    IntArmy c = new IntArmy(1, 3);
    a.mergeArmy(b);
    assertEquals(a.getUnitNum(), 6);
    assertEquals(b.getUnitNum(), 3);
    assertThrows(RuntimeException.class, () -> a.mergeArmy(c));
  }

  @Test
  public void test_print() {
    IntArmy a = new IntArmy(0, 3);
    assertEquals("(0: 3)", a.toString());
    // assertEquals(a.getUnitNum(), 6);
  }
}
