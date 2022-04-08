package team5.risc.common;

public interface Army{
  // interface for army
  public int getOwnerId();
  public int getLevel();
  public int getUnitNum();
  public int getBonus();

  public void mergeArmy(Army toMerge) throws RuntimeException;

  public void setOwner(int id);
  public void removeUnit(int num_to_remove) throws RuntimeException;
  public void addUnit(int num_to_add);
  public Army deepCopy();
}
