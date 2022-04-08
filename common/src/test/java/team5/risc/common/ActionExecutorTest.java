package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionExecutorTest {
 /* @Test
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
    AttackAction m2 = new AttackAction(1, "area1", "area6", 11);

    e.execute(m, map);
    e.execute(m2, map);
    assertEquals(10, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(3, map.getAreaNodeByName("area6").popFirstEnemy().getUnitNum());
    e.execute(m, map);
    assertEquals(1, map.getAreaNodeByName("area6").popFirstEnemy().getOwnerId());
  }

  @Test
  public void test_combat() {
    Map map = new Map();
    AttackAction m = new AttackAction(1, "area1", "area6", 13);
    AttackAction m2 = new AttackAction(0, "area6", "area1", 1);

    ActionExecutor e = new ActionExecutor();
    e.execute(m, map);
    e.execute(m2, map);
    e.resolveAllCombat(map, new CompareCombat());
    assertEquals(0, map.getAreaNodeByName("area6").getDefenderUnit());
    assertEquals(0, map.getAreaNodeByName("area6").getOwnerId());
    assertEquals(1, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(0, map.getAreaNodeByName("area1").getOwnerId());
    assertEquals("0: [area0, area3, area6, area1]", map.getRegions().get(0).toString());
    assertEquals("1: [area4, area7]", map.getRegions().get(1).toString());
    e.addUnitToAllArea(1, map);
    assertEquals(1, map.getAreaNodeByName("area6").getDefenderUnit());
  }*/

}
