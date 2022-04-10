package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MapTest {
  @Test
  public void test_Map() {
    Map m = new Map(2);
    assertEquals("area0", m.getAreasName().get(0));
    assertEquals("area1", m.getAreasName().get(1));

    ArrayList<AreaNode> areas = m.getAreas();
    assertEquals("area0", areas.get(0).getName());
    assertEquals(-1, areas.get(0).getOwnerId());

    Map m1 = new Map(4, 2);
    ArrayList<AreaNode> areas1 = m1.getAreas();
    assertEquals(-1, areas1.get(0).getOwnerId());
    assertEquals(-1, areas1.get(1).getOwnerId());
    assertEquals(-1, areas1.get(2).getOwnerId());
    assertEquals(-1, areas1.get(3).getOwnerId());
  }

  @Test
  public void test_region() {
    Map m = new Map(4, 2);
    Region r0 = m.getRegions().get(0);
    Region r1 = m.getRegions().get(1);
    assertEquals(r0.toString(), "-1: [area0, area2]");
    assertEquals(r1.toString(), "-1: [area1, area3]");
  }

  @Test
  public void test_map_generator() {
    Map m = new Map(2, 1);
    ArrayList<String> path = new ArrayList<String>();
    path.add("0->1");
    path.add("1->0");
    assertEquals(path, m.generateMap());
  }

  @Test
  public void test_toString() {
    Map map = new Map();
    String ans = new String();
    ans += "area0:(0: 10)\n[]\n" + "area1:(1: 13)\n[]\n" + "area2:(2: 6)\n[]\n" + "area3:(0: 12)\n[]\n"
        + "area4:(1: 8)\n[]\n" + "area5:(2: 5)\n[]\n" + "area6:(0: 14)\n[]\n" + "area7:(1: 3)\n[]\n"
        + "area8:(2: 3)\n[]\n";
    assertEquals(map.toString(), ans);
  }

 @Test
  public void test_win_lose() {
    Map map = new Map(true);
    ActionExecutor e = new ActionExecutor();
    MoveAction m1 = new MoveAction(0, "area0", "area2", 10);
    MoveAction m2 = new MoveAction(1, "area1", "area3", 1);
    AttackAction a1 = new AttackAction(0, "area2", "area3", 32);
    AttackAction a2 = new AttackAction(0, "area4", "area5", 22);
    AttackAction a3 = new AttackAction(0, "area0", "area1", 22);
    e.execute(m1, map);
    e.execute(m2, map);
    e.execute(a1, map);
    e.execute(a2, map);
    e.execute(a3, map);
    e.resolveAllCombat(map, new DiceCombat(6, 1));

    assertEquals(true, map.is_loser(1));
    assertEquals(false, map.is_loser(0));
    assertEquals(false, map.is_winner(1));
    assertEquals(true, map.is_winner(0));

    /*AttackAction a6 = new AttackAction(2, "area1", "area4", 3);
    e.execute(a6, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a7 = new AttackAction(2, "area4", "area3", 2);
    e.execute(a7, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a8 = new AttackAction(2, "area3", "area0", 2);
    e.execute(a8, map);
    e.resolveAllCombat(map, new CompareCombat());
    assertEquals(true, map.is_winner(2));*/

    // System.out.println(map.toString());
    //  assertEquals(0, 1);
  }

  @Test
  public void test_misc(){
    Map map = new Map();
    assertEquals(null, map.getAreaNodeByName("kkk"));
    assertEquals(3, map.getNumPlayer());
    
  }
}
