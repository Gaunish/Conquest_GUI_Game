package team5.risc.common;

import java.io.Serializable;

public class UpgradeAction implements Action, Serializable {
  int player_id;
  public String area;
  public int index_army;
  public int no_units;
  public int new_lvl;

  public UpgradeAction(int player_id, String area, int index_army, int no_units, int new_lvl) {
    this.player_id = player_id;
    this.area = area;
    this.index_army = index_army;
    this.no_units = no_units;
    this.new_lvl = new_lvl;
  }

  public String toString() {
    return player_id + " " + area + " " + index_army + " " + no_units + "-> level " + new_lvl;
  }
}
