package team5.risc.common;

import java.io.Serializable;
import java.util.ArrayList;

public class UnitArmy implements Army, Serializable {
  private int owner_id;
  private ArrayList<Unit> units;

  public UnitArmy(int owner_id, int unit_num) {
    this.owner_id = owner_id;
    this.units = new ArrayList<Unit>();
    for (int i = 0; i < unit_num; i++) {
      this.units.add(new Unit(i, 0));
    }
  }

  @Override
  public int getOwnerId() {
    return owner_id;
  }

  @Override
  public int getUnitNum() {
    return units.size();
  }

  @Override
  public void mergeArmy(Army toMerge) throws RuntimeException {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeUnit(int num_to_remove) throws RuntimeException {
    // TODO Auto-generated method stub

  }

  @Override
  public void addUnit(int num_to_add) {
    // TODO Auto-generated method stub

  }

  @Override
  public Army deepCopy() {
    // TODO Auto-generated method stub
    return null;
  }

}
