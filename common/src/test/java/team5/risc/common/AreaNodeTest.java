package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

public class AreaNodeTest {
  @Test
  public void test_getName() {
    AreaNode a = new AreaNode("a", -1);
    assertEquals("a", a.getName());
    assertEquals(-1, a.getOwnerId());
  }

  @Test
  public void test_equals_hasCode() {
    AreaNode a = new AreaNode("area0");
    AreaNode b = new AreaNode("area0");
    AreaNode c = new AreaNode("area1");
    AreaNode d = new AreaNode(null);
    assertEquals(false, a.equals(null));
    assertEquals(true, a.equals(b));
    assertEquals(false, b.equals(c));
    assertEquals(true, c.equals(c));
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(b.hashCode(), c.hashCode());
    assertEquals(-1, d.hashCode());
  }

  @Test
  public void test_defender() {
    AreaNode a = new AreaNode("a", -1);
    Army d = new IntArmy(1, 5);
    a.setDefender(d);
    assertEquals(1, a.getOwnerId());
  }

  @Test
  public void test_defender_2() {
    AreaNode a = new AreaNode("a");
    IntArmy d = new IntArmy(2, 4);
    a.setDefender(d);
    assertEquals(4, a.getDefenderUnit());
    assertEquals(d, a.getDefender());
    a.reduceDefender(2);
    assertEquals(2, a.getDefenderUnit());
    a.increaseDefender(10);
    assertEquals(12, a.getDefenderUnit());
  }

  @Test
  public void test_enemy() {
    AreaNode a = new AreaNode("a", -1);
    Army d = new IntArmy(0, 5);
    Army e1 = new IntArmy(1, 5);
    Army e2 = new IntArmy(1, 5);
    Army e3 = new IntArmy(2, 1);
    Army e4 = new IntArmy(3, 0);
    a.setDefender(d);
    assertEquals(0, a.getOwnerId());
    assertEquals(a.noEnemyLeft(), true);
    a.addEnemy(e1);
    a.addEnemy(e2);
    a.addEnemy(e4);
    a.addEnemy(e3);
    assertEquals(a.toString(), "a:(0: 5)\n[(1: 10), (3: 0), (2: 1)]");
    a.clearEmptyEnemyArmy();
    assertEquals(a.toString(), "a:(0: 5)\n[(1: 10), (2: 1)]");
    assertEquals(a.noEnemyLeft(), false);
    a.popFirstEnemy();
    assertEquals(a.noEnemyLeft(), false);
    assertEquals(e3.equals(a.popFirstEnemy()), true);
    assertEquals(a.noEnemyLeft(), true);
    assertEquals(a.popFirstEnemy(), null);
  }

  @Test
  public void test_neighbors() {
    AreaNode a = new AreaNode("a", -1);
    AreaNode b = new AreaNode("b", -1);
    AreaNode c = new AreaNode("c", -1);
    a.addNeighbor(b);
    a.addNeighbor(c);
    a.addNeighbor(b);
    assertEquals(a.getNeighborsName().toString(), "[b, c]");
    LinkedHashSet<AreaNode> nei = new LinkedHashSet<AreaNode>();
    nei.add(b);
    nei.add(c);
    assertEquals(nei.equals(a.getNeighbors()), true);
  }

}
