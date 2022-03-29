package team5.risc.common;

import java.util.HashMap;

public class Unit {
  private int id;
  private int level;
  private HashMap<Integer, Integer> level_bonus_map;
  private HashMap<Integer, Integer> level_cost_map;// total cost
  private HashMap<Integer, String> level_name_map;

  public Unit(int id, int level) {
    this.id = id;
    initConst();
    this.level = level;
  }

  private void initConst() {
    this.level_bonus_map = new HashMap<Integer, Integer>();
    level_bonus_map.put(0, 0);
    level_bonus_map.put(1, 1);
    level_bonus_map.put(2, 3);
    level_bonus_map.put(3, 5);
    level_bonus_map.put(4, 8);
    level_bonus_map.put(5, 11);
    level_bonus_map.put(6, 15);

    this.level_cost_map = new HashMap<Integer, Integer>();
    level_cost_map.put(0, 0);
    level_cost_map.put(1, 3);
    level_cost_map.put(2, 11);
    level_cost_map.put(3, 30);
    level_cost_map.put(4, 55);
    level_cost_map.put(5, 90);
    level_cost_map.put(6, 140);

    this.level_name_map = new HashMap<Integer, String>();
    for (int i = 0; i < 7; i++) {
      level_name_map.put(i, "level_" + i);
    }
  }

  public int getId() {
    return id;
  }

  public int getLevel() {
    return level;
  }

  public void upgradeLevel(int new_level) {
    if (new_level > level) {
      level = new_level;
    } else {
      return;
    }
  }

  public int getBonus() {
    return level_bonus_map.get(level);
  }

  public String getName() {
    return level_name_map.get(level);
  }

  public int needCost(int to_level) {
    if (to_level <= level) {
      return 0;
    } else {
      return level_cost_map.get(to_level) - level_cost_map.get(level);
    }
  }
}
