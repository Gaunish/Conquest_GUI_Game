package team5.risc.common;

public class CompareCombat implements Combat {
  public Army doCombat(Army defender, Army attacker) {
    Army winner = new IntArmy(-1, 0);
    if (defender.getUnitNum() >= attacker.getUnitNum()) {
      int to_move = attacker.getUnitNum();
      defender.removeUnit(to_move);
      attacker.removeUnit(to_move);
      winner = defender;
    } else {
      int to_move = defender.getUnitNum();
      defender.removeUnit(to_move);
      attacker.removeUnit(to_move);
      winner = attacker;
    }
    return winner;
  }
}
