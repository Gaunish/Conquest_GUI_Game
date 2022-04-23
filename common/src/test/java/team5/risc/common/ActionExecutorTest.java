package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionExecutorTest {
 @Test
  public void test_move_execute() {
    Map map = new Map(true);
    MoveAction m = new MoveAction(0, "area0", "area2", 10);
    ActionExecutor e = new ActionExecutor();
    e.execute(m, map);
    assertEquals(22, map.getAreaNodeByName("area0").getDefenderUnit());
    assertEquals(32, map.getAreaNodeByName("area2").getDefenderUnit());
  }

  @Test
  public void test_attack_execute() {
    Map map = new Map(true);
    AttackAction m = new AttackAction(0, "area0", "area1", 5);
    ActionExecutor e = new ActionExecutor();
    AttackAction m2 = new AttackAction(0, "area2", "area3", 5);

    e.execute(m, map);
    e.execute(m2, map);
    assertEquals(27, map.getAreaNodeByName("area0").getDefenderUnit());
    assertEquals(5, map.getAreaNodeByName("area1").popFirstEnemy().getUnitNum());
    e.execute(m, map);
    assertEquals(0, map.getAreaNodeByName("area1").popFirstEnemy().getOwnerId());
  }

  @Test
  public void test_combat() {
    Map map = new Map(true);
    AttackAction m = new AttackAction(0, "area0", "area1", 10);
    AttackAction m2 = new AttackAction(1, "area1", "area0", 1);
    View view1 = new View(map.getRegionById(0), map.getRegionById(1), 0, map, 6);
    View view2 = new View(map.getRegionById(1), map.getRegionById(0), 1, map, 6);

    ActionExecutor e = new ActionExecutor();
    e.execute(m, map);
    e.execute(m2, map);

    e.resolveAllCombat(map, new CompareCombat(), view1, view2);
    assertEquals(21, map.getAreaNodeByName("area0").getDefenderUnit());
    assertEquals(0, map.getAreaNodeByName("area0").getOwnerId());
    assertEquals(10, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(0, map.getAreaNodeByName("area1").getOwnerId());
    assertEquals("0: [area0, area2, area4, area1]", map.getRegions().get(0).toString());
    assertEquals("1: [area3, area5]", map.getRegions().get(1).toString());
    e.addUnitToAllArea(1, map);
    assertEquals(22, map.getAreaNodeByName("area0").getDefenderUnit());

    Map map2 = new Map(true);
    AreaNode a1 = map2.getAreaNodeByName("area1");
    a1.setDefender(new LevelArmy(1, 5, 5), 5);
    AttackAction e1 = new AttackAction(0, "area0", "area1", 10);
    e.execute(e1, map2);

    e.resolveAllCombat(map2, new CompareCombat(), view1, view2);
    assertEquals("0: [area0, area2, area4]", map2.getRegions().get(0).toString());
    assertEquals("1: [area1, area3, area5]", map2.getRegions().get(1).toString());
    assertEquals(0, a1.getDefenderUnit());
    assertEquals(5, a1.getDefenderUnit(5));
    assertEquals(1, a1.getOwnerId());
  }

  @Test
  public void test_upgrade(){
    Map map = new Map(true);

    UpgradeAction u = new UpgradeAction(0, "area0", 0, 4, 2);
    UpgradeAction u2 = new UpgradeAction(1, "area1", 0, 1, 1);
    ActionExecutor e = new ActionExecutor();
    e.execute(u, map);
    e.execute(u2, map);
    assertEquals(28, map.getAreaNodeByName("area0").getDefenderUnit());
    assertEquals(0, map.getAreaNodeByName("area0").getDefenderUnit(1));
    assertEquals(4, map.getAreaNodeByName("area0").getDefenderUnit(2));
    assertEquals(0, map.getAreaNodeByName("area1").getDefenderUnit());
    assertEquals(1, map.getAreaNodeByName("area1").getDefenderUnit(1));

    AttackAction a = new AttackAction(0, "area0", "area1", 2, 4);
    AttackAction a2 = new AttackAction(0, "area0", "area1", 0, 10);
    AttackAction a3 = new AttackAction(1, "area1", "area0", 1, 1);
    AttackAction a4 = new AttackAction(0, "area2", "area3", 0, 20);
    AttackAction a5 = new AttackAction(0, "area4", "area5", 0, 20);

    AttackAction a_invalid = new AttackAction(0, "area0", "area1", 5, 4);
    e.execute(a_invalid, map);
    View view1 = new View(map.getRegionById(0), map.getRegionById(1), 0, map, 6);
    View view2 = new View(map.getRegionById(1), map.getRegionById(0), 1, map, 6);
  

    e.execute(a, map);
    e.execute(a2, map);
    e.execute(a3, map);
    e.execute(a4, map);
    e.execute(a5, map);
    e.resolveAllCombat(map, new DiceCombat(20, 1), view1, view2);
    assertEquals(true, map.is_winner(0));

  }

  @Test
  public void test_update(){
    Map map = new Map(true);
    ActionExecutor e = new ActionExecutor();
    e.updateResources(map);
    assertEquals(true, map.getRegionById(0).checkFoodEnough(130));
    assertEquals(true, map.getRegionById(0).checkTechEnough(130));
    assertEquals(false, map.getRegionById(0).checkFoodEnough(131));
    assertEquals(false, map.getRegionById(0).checkFoodEnough(131));
    assertEquals(true, map.getRegionById(1).checkFoodEnough(130));

    assertEquals(false, map.getRegionById(0).subFood(160));
    assertEquals(false, map.getRegionById(0).subTech(160));
    assertEquals(true, map.getRegionById(0).subFood(130));
    assertEquals(true, map.getRegionById(0).subTech(130));
  }

}
