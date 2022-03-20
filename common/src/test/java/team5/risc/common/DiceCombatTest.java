package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DiceCombatTest {
  @Test
  public void test_doCombat() {
    DiceCombat d = new DiceCombat(5, 3);
    assertEquals(5, d.getDice());
    assertEquals(3, d.getUnit());
    d.setDice(6);
    d.setUnit(1);
    Army defender = new IntArmy(0, 1);
    Army attacker = new IntArmy(1, 100);
    Army winner = d.doCombat(defender, attacker);
    assertEquals(true, winner.equals(attacker));
  }

  @Test
  public void test_doCombat_2() {
    DiceCombat d = new DiceCombat(5, 1);
    Army defender = new IntArmy(0, 100);
    Army attacker = new IntArmy(1, 1);
    Army winner = d.doCombat(defender, attacker);
    assertEquals(true, winner.equals(defender));
  }

}
