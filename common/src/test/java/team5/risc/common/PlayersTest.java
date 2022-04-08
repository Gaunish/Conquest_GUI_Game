package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayersTest {
 /* @Test
  public void test_lose_win() {
    Map map = new Map();
    Players p = new Players(3);
    ActionExecutor e = new ActionExecutor();
    MoveAction m1 = new MoveAction(0, "area0", "area3", 10);
    MoveAction m2 = new MoveAction(1, "area1", "area4", 13);
    AttackAction a1 = new AttackAction(0, "area3", "area4", 22);

    AttackAction a2 = new AttackAction(1, "area7", "area6", 3);
    MoveAction m3 = new MoveAction(2, "area2", "area5", 6);
    MoveAction m4 = new MoveAction(2, "area8", "area5", 3);
    AttackAction a3 = new AttackAction(2, "area5", "area6", 12);
    e.execute(m1, map);
    e.execute(m2, map);
    e.execute(m3, map);
    e.execute(m4, map);
    e.execute(a1, map);
    e.execute(a2, map);
    e.execute(a3, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a4 = new AttackAction(2, "area5", "area7", 2);
    e.execute(a4, map);
    e.resolveAllCombat(map, new CompareCombat());

    MoveAction m5 = new MoveAction(2, "area7", "area6", 2);
    e.execute(m5, map);
    AttackAction a5 = new AttackAction(2, "area6", "area1", 3);
    e.execute(a5, map);
    e.resolveAllCombat(map, new CompareCombat());
    assertEquals(true, map.is_loser(1));
    assertEquals(false, map.is_loser(0));
    assertEquals(false, map.is_winner(2));
    assertEquals(true, p.has_lost(map, 1));
    assertEquals(false, p.has_lost(map, 2));
    assertEquals(-1, p.get_winner(map, 3));

    AttackAction a6 = new AttackAction(2, "area1", "area4", 3);
    e.execute(a6, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a7 = new AttackAction(2, "area4", "area3", 2);
    e.execute(a7, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a8 = new AttackAction(2, "area3", "area0", 2);
    e.execute(a8, map);
    e.resolveAllCombat(map, new CompareCombat());
    assertEquals(true, p.has_lost(map, 0));
    assertEquals(true, p.has_lost(map, 0));
    assertEquals(true, map.is_winner(2));
    assertEquals(2, p.get_winner(map, 3));
    assertEquals(2, p.get_winner(map, 3));
  }*/

}
