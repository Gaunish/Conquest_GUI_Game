package team5.risc.common;

public interface Army {
  public int getOwnerId();

  public int getUnitNum();

  public void mergeArmy(Army toMerge) throws RuntimeException;
}
