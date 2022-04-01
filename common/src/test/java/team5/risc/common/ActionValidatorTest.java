package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
    assertEquals("area1 doesn't have enough unit (level 0) to move", v.isValid(m3, map));
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
    AttackAction m4 = new AttackAction(2, "area8", "area7", 13);
    assertEquals(null, v.isValid(m4, map));
  }

}
