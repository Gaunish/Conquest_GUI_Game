package team5.risc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.Serializable;

public class AreaNode implements Serializable{

  private String name;
  private Army defender;
  private ArrayList<Army> enemies;
  private LinkedHashSet<AreaNode> neighbors;

  public AreaNode(String name) {
    this.name = name;
    this.defender = new IntArmy(-1, 0); // owner_id -1 means this area has no owner
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
  }
  
  public AreaNode(String name, int id) {
    this.name = name;
    this.defender = new IntArmy(id, id * 2); // owner_id -1 means this area has no owner
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
  }

  public String getName() {
    return name;
  }

  public int getOwnerId() {
    return defender.getOwnerId();
  }

  public int getUnitNo(){
    return defender.getUnitNum();
  }

  public void setDefender(Army new_defender) {
    defender = new_defender;
  }

  public void addEnemy(Army to_add) {
    Iterator<Army> it = enemies.iterator();
    while (it.hasNext()) {
      Army cur_enemy = it.next();
      if (cur_enemy.getOwnerId() == to_add.getOwnerId()) {
        cur_enemy.mergeArmy(to_add);
        return;
      }
    }
    enemies.add(to_add);
  }

  public void clearEmptyEnemyArmy() {
    Iterator<Army> it = enemies.iterator();
    while (it.hasNext()) {
      Army cur_enemy = it.next();
      if (cur_enemy.getUnitNum() == 0) {
        it.remove();
      }
    }
  }
  /*
   * public void removeEnemy(Army to_remove){ enemies.remove(to_remove); }
   */

  public Boolean noEnemyLeft() {
    return enemies.size() == 0;
  }

  public String toString() {
    return name + ":" + defender + "\n" + enemies;
  }

  public void addNeighbor(AreaNode to_add) {
    neighbors.add(to_add);
  }

  public ArrayList<String> getNeighborsName() {
    ArrayList<String> neighbors_name = new ArrayList<String>();
    Iterator<AreaNode> it = neighbors.iterator();
    while (it.hasNext()) {
      neighbors_name.add(it.next().getName());
    }
    return neighbors_name;
  }
}
