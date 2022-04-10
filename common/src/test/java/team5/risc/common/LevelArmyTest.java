package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevelArmyTest {
  @Test
  public void test_main() {
    LevelArmy a = new LevelArmy(0, 1);
    assertEquals("Level 0", a.getName());
    assertEquals(a.getOwnerId(), 0);
    assertEquals(a.getUnitNum(), 1);
    a.removeUnit(1);
    assertEquals(a.getUnitNum(), 0);
    a.removeUnit(1);
    assertEquals(a.getUnitNum(), 0);
    a.addUnit(5);
    a.removeUnit(3);
    assertEquals(a.getUnitNum(), 2);
    assertEquals(0, a.getBonus());
    assertEquals(0, a.getLevel());

    a.setOwner(1);
    assertEquals(a.getOwnerId(), 1);

    a.upgradeLevel(3);
    assertEquals(5, a.getBonus());
    assertEquals(3, a.getLevel());

    assertEquals(110, a.cost(6));

    a.upgradeLevel(1);
    assertEquals(8, a.getBonus());
    assertEquals(4, a.getLevel());
    a.upgradeLevel(1);
    assertEquals(11, a.getBonus());
    assertEquals(5, a.getLevel());
    a.upgradeLevel(1);
    assertEquals(15, a.getBonus());
    assertEquals(6, a.getLevel());

    a.addUnit(5);
    assertEquals(a.getUnitNum(), 7);

    LevelArmy b = new LevelArmy(1, 5, 5);
    assertThrows(RuntimeException.class, () -> a.mergeArmy(b));

    LevelArmy c = new LevelArmy(0, 5, 2);
    assertThrows(RuntimeException.class, () -> a.mergeArmy(c));

    LevelArmy d = new LevelArmy(1, 5, 6);
    //System.out.println(a.getLevel() + " " + a.getOwnerId() + " " + d.getOwnerId() + " " + d.getLevel());
    a.mergeArmy(d);
    assertEquals(12, a.getUnitNum());

    a.removeUnit(1);
    assertEquals(11, a.getUnitNum());
    a.removeUnit(100);
    assertEquals(0, a.getUnitNum());

    Army e = a.deepCopy();
    assertNotEquals(e, a);

    assertEquals("(1: 0)", a.toString());
  }
}
