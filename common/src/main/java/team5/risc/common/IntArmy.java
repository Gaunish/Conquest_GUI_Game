package team5.risc.common;

import java.io.Serializable;

public class IntArmy implements Army, Serializable {
  private int owner_id;
  private int unit_num;

  public IntArmy(int owner_id, int unit_num) {
    this.owner_id = owner_id; // owner_id -1 means this area has no owner
    this.unit_num = unit_num;
  }

  public int getOwnerId() {
    return owner_id;
  }

  public void setOwner(int id){
      this.owner_id = id;
  }
  
  public int getBonus(){
    return 0;
  }


  public int getUnitNum() {
    return unit_num;
  }

  public int getLevel(){
    return 0;
  }

  public void removeUnit(int num_to_remove) throws RuntimeException {
    if (unit_num - num_to_remove >= 0) {
      unit_num -= num_to_remove;
    } else {
      unit_num = 0;
      // throw new UnsupportedOperationException("No enough unit to lose");
    }
  }

  public void addUnit(int num_to_add) {
    unit_num += num_to_add;
  }

  public void mergeArmy(Army to_merge) throws RuntimeException {
    if (owner_id != to_merge.getOwnerId()) {
      throw new UnsupportedOperationException("Armies don't have the same owner. Merge request is refused.");
    }
    unit_num += to_merge.getUnitNum();
  }

  public IntArmy deepCopy() {
    return new IntArmy(owner_id, unit_num);
  }

  public String toString() {
    return "(" + owner_id + ": " + unit_num + ")";
  }
}
