package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullEnum;

public class ActionValidatorTest {

  @Test
  public void test_getName() {
    AreaNode a = new AreaNode("a", -1);
    assertEquals("a", a.getName());
    assertEquals(-1, a.getOwnerId());
  }

  @Test
  public void test_move() {
    Map map = new Map();
    ActionValidator v = new ActionValidator();
    MoveAction m1 = new MoveAction(0, "area1", "area4", 3);
    assertEquals("Player 0 has no access to area1, which is owned by Player 1", v.isValid(m1, map));
    MoveAction m2 = new MoveAction(1, "area1", "area6", 3);
    assertEquals("Player 1 can't move unit to area6, which is owned by Player 0", v.isValid(m2, map));
    MoveAction m3 = new MoveAction(1, "area1", "area4", 14);
    assertEquals("area1 doesn't have enough (level 0) units to move", v.isValid(m3, map));
    MoveAction m4 = new MoveAction(1, "area1", "area7", 3);
    assertEquals("Unreachable", v.isValid(m4, map));
    MoveAction m5 = new MoveAction(2, "area8", "area2", 3);
    assertEquals(null, v.isValid(m5, map));
  }

  @Test
  public void test_attack() {
    Map map = new Map();
    ActionValidator v = new ActionValidator();
    AttackAction m1 = new AttackAction(0, "area1", "area4", 3);
    assertEquals("Player 0 has no access to area1, which is owned by Player 1", v.isValid(m1, map));
    AttackAction m2 = new AttackAction(1, "area1", "area4", 3);
    assertEquals("area4 also belongs to Player 1, please attack other players' areas", v.isValid(m2, map));
    AttackAction m3 = new AttackAction(1, "area1", "area3", 3);
    assertEquals("Unreachable", v.isValid(m3, map));
    AttackAction m4 = new AttackAction(2, "area8", "area7", 1);
    assertEquals(null, v.isValid(m4, map));
  }

  @Test
  public void test_upgrade() {
    Map map = new Map(true);
    ActionValidator v = new ActionValidator();
    UpgradeAction u = new UpgradeAction(0, "area1", 0, 1, 2);
    assertEquals("Player 0 has no access to area1, which is owned by Player 1", v.isValid(u, map));

    UpgradeAction u1 = new UpgradeAction(0, "area0", 0, 50, 1);
    assertEquals("area0 only have 32 unit (level 0)", v.isValid(u1, map));

    UpgradeAction u2 = new UpgradeAction(0, "area0", 1, 1, 2);
    assertEquals("area0 only have 0 unit (level 1)", v.isValid(u2, map));

    UpgradeAction u3 = new UpgradeAction(0, "area0", 0, 32, 5);
    assertEquals("no enough tech", v.isValid(u3, map));

    UpgradeAction u4 = new UpgradeAction(0, "area0", 0, 1, 9);
    assertEquals("max tech level is 6", v.isValid(u4, map));

    UpgradeAction u5 = new UpgradeAction(0, "area0", 0, 1, 0);
    assertEquals("those units are already in level 0", v.isValid(u5, map));

    UpgradeAction u6 = new UpgradeAction(0, "area0", 0, 10, 1);
    assertEquals(null, v.isValid(u6, map));

  }
}
