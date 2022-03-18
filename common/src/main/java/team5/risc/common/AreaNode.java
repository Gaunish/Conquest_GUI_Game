package team5.risc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.Serializable;

public class AreaNode implements Serializable {

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

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    AreaNode other = (AreaNode) obj;
    return other.name.equals(this.name);
  }

  @Override
  public int hashCode() {
    if (name == null)
      return -1;
    return name.hashCode();
  }

  public AreaNode(String name, int id) {
    this.name = name;
    this.defender = new IntArmy(id, id * 2); // owner_id -1 means this area has no owner
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
  }

  public Army getArmy() {
    return defender;
  }

  public String getName() {
    return name;
  }

  public int getOwnerId() {
    return defender.getOwnerId();
  }

  public int getDefenderUnit() {
    return defender.getUnitNum();
  }

  public void setDefender(Army new_defender) {
    defender = new_defender;
  }

  public Army getDefender() {
    return defender;
  }

  public void reduceDefender(int reduce_num) {
    defender.removeUnit(reduce_num);
  }

  public void increaseDefender(int increase_num) {
    defender.addUnit(increase_num);
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

  public void removeEnemy(Army to_remove) {
    enemies.remove(to_remove);
  }

  public Boolean noEnemyLeft() {
    return enemies.size() == 0;
  }

  public Army popFirstEnemy() {
    if (enemies.size() > 0) {
      Army the_enemy = enemies.get(0);
      enemies.remove(the_enemy);
      return the_enemy;
    } else {
      return null;
    }
  }

  public AreaNode deepCopy() {
    AreaNode new_node = new AreaNode(name);
    Army new_defender = defender.deepCopy();
    new_node.setDefender(new_defender);
    Iterator<Army> it = enemies.iterator();
    while (it.hasNext()) {
      Army cur_enemy = it.next();
      Army new_enemy = cur_enemy.deepCopy();
      new_node.addEnemy(new_enemy);
    }
    // can't add neighbor info here, neighbor info will be add in Map
    return new_node;
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

  public LinkedHashSet<AreaNode> getNeighbors() {
    return neighbors;
  }

}
