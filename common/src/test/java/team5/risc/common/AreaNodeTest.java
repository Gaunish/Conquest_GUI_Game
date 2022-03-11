package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AreaNodeTest {
  @Test
  public void test_getName() {
    AreaNode a = new AreaNode("a", -1);
    assertEquals("a", a.getName());
    assertEquals(-1, a.getOwnerId());
  }

  @Test
  public void test_defender() {
    AreaNode a = new AreaNode("a", -1);
    Army d = new IntArmy(1, 5);
    a.setDefender(d);
    assertEquals(1, a.getOwnerId());
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
  }

}
