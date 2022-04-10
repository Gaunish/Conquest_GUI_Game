package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayersTest {
 @Test
  public void test_lose_win() {
    Map map = new Map(true);
    Players p = new Players(2);
    assertEquals(-1, p.get_winner(map, 2));

    ActionExecutor e = new ActionExecutor();
    MoveAction m1 = new MoveAction(0, "area0", "area2", 10);
    MoveAction m2 = new MoveAction(1, "area1", "area3", 1);
    AttackAction a1 = new AttackAction(0, "area2", "area3", 32);
    e.execute(m1, map);
    e.execute(m2, map);
    e.execute(a1, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a2 = new AttackAction(0, "area3", "area5", 10);
    e.execute(a2, map);
    e.resolveAllCombat(map, new CompareCombat());

    AttackAction a3 = new AttackAction(0, "area3", "area1", 12);
    e.execute(a3, map);
    e.resolveAllCombat(map, new CompareCombat());

    assertEquals(0, p.get_winner(map, 2));
    assertEquals(true, p.has_lost(map, 1));
    assertEquals(true, p.has_lost(map, 1));
    p.add_loser(1);
    assertEquals(false, p.has_lost(map, 0));
    assertEquals(false, p.has_won(map, 1));
   
    assertEquals(true, p.has_won(map, 0));
    assertEquals(0, p.get_winner(map, 2));
  }

}
