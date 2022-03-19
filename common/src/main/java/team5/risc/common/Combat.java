package team5.risc.common;

public interface Combat {
  public Army doCombat(Army defender, Army attacker);

  public Army doSimpleCombat(Army defender, Army attacker);
}
