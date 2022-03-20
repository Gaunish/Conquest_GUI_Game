package team5.risc.common;

public class CompareCombat implements Combat {
  public Army doCombat(Army defender, Army attacker) {
    Army winner = new IntArmy(-1, 0);
    if (defender.getUnitNum() >= attacker.getUnitNum()) {
      defender.removeUnit(attacker.getUnitNum());
      attacker.removeUnit(attacker.getUnitNum());
      winner = defender;
    } else {
      defender.removeUnit(defender.getUnitNum());
      attacker.removeUnit(defender.getUnitNum());
      winner = attacker;
    }
    return winner;
  }
}
