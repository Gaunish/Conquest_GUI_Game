package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.checkerframework.checker.units.qual.Area;
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
    
    MoveAction m5 = new MoveAction(1, "area10", "area7", 3);
    assertEquals("Invalid source name", v.isValid(m5, map));

    MoveAction m6 = new MoveAction(1, "area1", "area10", 3);
    assertEquals("Invalid destination name", v.isValid(m6, map));

    MoveAction m8 = new MoveAction(2, "area8", "area2", 3);
    assertEquals(null, v.isValid(m8, map));

    Map map2 = new Map(true);
    MoveAction m_in = new MoveAction(0, "area0", "area4", 32);
    assertEquals("Not enough food", v.isValid(m_in, map2));
   
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

    AttackAction m5 = new AttackAction(1, "area10", "area7", 3);
    assertEquals("Invalid source name", v.isValid(m5, map));

    AttackAction m6 = new AttackAction(1, "area1", "area10", 3);
    assertEquals("Invalid destination name", v.isValid(m6, map));

    Map map2 = new Map(true);
    AttackAction m_in = new AttackAction(0, "area0", "area1", 200);
    assertEquals("Not enough food for attacking", v.isValid(m_in, map2));

    AttackAction m_in2 = new AttackAction(0, "area0", "area1", 52);
    assertEquals("area0 doesn't have enough (level 0) units to move", v.isValid(m_in2, map2));
   

  }

  @Test
  public void test_upgrade() {
    Map map = new Map(true);
    ActionValidator v = new ActionValidator();

    UpgradeAction u_in = new UpgradeAction(0, "area10", 0, 1, 2);
    assertEquals("Invalid source name", v.isValid(u_in, map));
    
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

  @Test
  public void test_spy(){
    Map map = new Map(true);
    ActionValidator v = new ActionValidator();

    SpyAction a1 = new SpyAction(0, "area6", "area1");
    assertEquals("invalid source name", v.isValid(a1, map));

    SpyAction a2 = new SpyAction(0, "area0", "area6");
    assertEquals("invalid destination name", v.isValid(a2, map));

    AreaNode area1 = map.getAreaNodeByName("area1");
    area1.reduceDefender(1);
    SpyAction a3 = new SpyAction(1, "area1", "area0");
    assertEquals("not enough units", v.isValid(a3, map));

    SpyAction a4 = new SpyAction(0, "area3", "area5");
    assertEquals("src area doesn't belong to player", v.isValid(a4, map));

    SpyAction a5 = new SpyAction(0, "area0", "area4");
    assertEquals("destnation area belongs to you", v.isValid(a5, map));

    Region r = map.getRegionById(0);
    r.subTech(100);
    SpyAction a6 = new SpyAction(0, "area0", "area1");
    assertEquals("not enough tech for spy", v.isValid(a6, map));

    SpyAction a7 = new SpyAction(1, "area3", "area0");
    assertEquals(null, v.isValid(a7, map));

  }

  @Test
  public void test_cloak() {
    Map map = new Map(true);
    ActionValidator v = new ActionValidator();

    CloakAction a1 = new CloakAction(0, "area6");
    assertEquals("invalid area name", v.isValid(a1, map));

    CloakAction a2 = new CloakAction(0, "area1");
    assertEquals("area doesn't belong to player", v.isValid(a2, map));

    Region r = map.getRegionById(0);
    r.subTech(100);

    CloakAction a3 = new CloakAction(0, "area0");
    assertEquals("not enough tech for cloak", v.isValid(a3, map));

    CloakAction a4 = new CloakAction(1, "area1");
    assertEquals(null, v.isValid(a4, map));

  }
}
