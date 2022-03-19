package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionExecutorTest {
  @Test
  public void test_move_execute() {
    Map map = new Map();
    MoveAction m = new MoveAction(1, "area1", "area4", 3);
    ActionExecutor e = new ActionExecutor();
    e.execute(m, map);
    assertEquals(10, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(11, map.getAreaNodeByName("area4").getDefenderUnit());
  }

  @Test
  public void test_attack_execute() {
    Map map = new Map();
    AttackAction m = new AttackAction(1, "area1", "area6", 3);
    ActionExecutor e = new ActionExecutor();
    e.execute(m, map);
    assertEquals(10, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(3, map.getAreaNodeByName("area6").popFirstEnemy().getUnitNum());
    e.execute(m, map);
    assertEquals(1, map.getAreaNodeByName("area6").popFirstEnemy().getOwnerId());
  }
}
