package team5.risc.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.io.Serializable;

public class AreaNode implements Serializable {

  private String name;
  private ArrayList<Army> defender;
  private ArrayList<Army> enemies;
  private LinkedHashSet<AreaNode> neighbors;
  private int food_produce;
  private int tech_produce;
  private int size;
  private int no_level;
  private List<Integer> cost;

  public AreaNode(String name) {
    this.name = name;
    this.defender = new ArrayList<Army>();
    this.defender.add(new IntArmy(-1, 0)); // owner_id -1 means this area has no owner
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
    this.food_produce = 2;
    this.tech_produce = 3;
    this.size = 1;
    this.no_level = 1;
    cost = Arrays.asList(0);
  }

  public AreaNode(String name, int id) {
    this.name = name;
    this.defender = new ArrayList<Army>();
    this.defender.add(new IntArmy(-1, 0)); // owner_id -1 means this area has no owner
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
    this.food_produce = 2;
    this.tech_produce = 3;
    this.size = 1;
    this.no_level = 1;
    cost =  Arrays.asList(0);
  }

  public AreaNode(String name, int food, int tech, int size) {
    this.name = name;
    this.defender = new ArrayList<Army>();
    this.enemies = new ArrayList<Army>();
    this.neighbors = new LinkedHashSet<AreaNode>();
    this.food_produce = food;
    this.tech_produce = tech;
    this.size = size;
    this.no_level = 7;
    cost =  Arrays.asList(0, 3, 11, 30, 55, 90, 140);

    initLevelDefender(-1);
  }

  //Method to init level defender list
  public void initLevelDefender(int owner_id){
    for(int lvl = 0; lvl < no_level; lvl++){
      defender.add(new LevelArmy(owner_id, 0, 0));
    }
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

  //Method to calculate cost to upgrade 
  public int costLevel(int lvl, int new_lvl){
    if(lvl < 0 || lvl >= no_level || new_lvl < 0 || new_lvl >= no_level){
      return -1;
    }
    return (cost.get(new_lvl) - cost.get(lvl));
  }

  /*
  //Method to check if upgrade is valid
  //Checks if enough units of given lvl
  //Checks if new_lvl is valid
  //Check if enough tech resource
  public boolean isUpgradeValid(int lvl, int new_lvl, int no_unit, int tech){
    //Check lvl
    if(new_lvl >= no_level){
      return false;
    }

    //Check no of unit
    Army army = defender.get(lvl);
    if(army.getUnitNum() < no_unit){
      return false;
    }

    //Check tech
    if(no_unit * cost(lvl, new_lvl) > tech){
      return false;
    }

    return true;
  }*/

  //Method to upgrade units of given lvl
  public void upgradeArmy(int lvl, int new_lvl, int no_unit){   
    reduceDefender(no_unit, lvl);
    increaseDefender(no_unit, new_lvl);
  }

  public String getName() {
    return name;
  }

  public int getOwnerId() {
    return defender.get(0).getOwnerId();
  }

  public int getDefenderUnit() {
    return getDefenderUnit(0);
  }

  //Get given lvl defender no
  public int getDefenderUnit(int lvl){
    return defender.get(lvl).getUnitNum();
  }

  public void setOwner(int id){
    for(Army a : defender){
      a.setOwner(id);
    }
  }

  public void setDefender(Army new_defender) {
    setDefender(new_defender, new_defender.getLevel());
    setOwner(new_defender.getOwnerId());
  }

  //set given lvl defender
  public void setDefender(Army new_defender, int lvl) {
    defender.set(lvl, new_defender);
  }

  public boolean hasLost(){
    for(Army a : defender){
      if(a.getUnitNum() > 0){
        return false;
      }
    }
    return true;
  }

  public Army getDefender() {
    return getDefender(0);
  }

  //Get given lvl defender
  public Army getDefender(int lvl) {
    return defender.get(lvl);
  }

  //Get highest/lowest bonus defender
  public Army getBonusDefender(boolean pos){
    if(pos){
      for(int i = no_level - 1; i >= 0; i--){
        //System.out.println(name + " " + i);
        Army a = getDefender(i);
        if(a.getUnitNum() > 0){
          return a;
        }
      }
    }
    else{
      for(int i = 0; i < no_level; i++){
        Army a = getDefender(i);
        if (a.getUnitNum() > 0) {
          return a;
        }
      }
    }
    return getDefender();
  }

  // Get highest/lowest bonus attacker
  public Army getBonusEnemy(boolean pos){
    if(pos){
      Army max = null;
      int max_lvl = -1;
      for(Army a : enemies){
        if(a.getLevel() > max_lvl){
          max_lvl = a.getLevel();
          max = a;
          if(max_lvl == 6){
            break;
          }
        }
      }
      enemies.remove(max);
      return max;
    }
    else{
      Army min = null;
      int min_lvl = 7;
      for (Army a : enemies) {
        if (a.getLevel() < min_lvl) {
          min_lvl = a.getLevel();
          min = a;
          if (min_lvl == 0) {
            break;
          }
        }
      }
      enemies.remove(min);
      return min;
    }
  }

  public void reduceDefender(int reduce_num) {
    reduceDefender(reduce_num, 0);
  }

  //Reduce given lvl defender num
  public void reduceDefender(int reduce_num, int lvl) {
    Army def = defender.get(lvl); 
    def.removeUnit(reduce_num);
    defender.set(lvl, def);
  }

  public void increaseDefender(int increase_num) {
    increaseDefender(increase_num, 0);
  }

  //Increase given lvl defender num
  public void increaseDefender(int increase_num, int lvl) {
    Army def = defender.get(lvl); 
    def.addUnit(increase_num);
    defender.set(lvl, def);
  }

  public void addEnemy(Army to_add) {
    Iterator<Army> it = enemies.iterator();
    while (it.hasNext()) {
      Army cur_enemy = it.next();
      if (cur_enemy.getOwnerId() == to_add.getOwnerId() && cur_enemy.getLevel() == to_add.getLevel()) {
        try{
          cur_enemy.mergeArmy(to_add);
        }
        catch(Exception e){}
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

  /*
   * public AreaNode deepCopy() { AreaNode new_node = new AreaNode(name); Army
   * new_defender = defender.deepCopy(); new_node.setDefender(new_defender);
   * Iterator<Army> it = enemies.iterator(); while (it.hasNext()) { Army cur_enemy
   * = it.next(); Army new_enemy = cur_enemy.deepCopy();
   * new_node.addEnemy(new_enemy); } // can't add neighbor info here, neighbor
   * info will be add in Map return new_node; }
   */
  public String toString() {
    return toString(0);
  }

  public String toString(int lvl) {
    return name + ":" + defender.get(lvl) + "\n" + enemies;
  }
  
  public String displayRsrc(){
    String txt = new String();
    txt += "Defender:\n";
    txt += getDefenderText();
    txt += "Food production: " + getFood() + "\n";
    txt += "Tech production: " + getTech() + "\n";
    txt += "Size: " + getSize() + "\n\n";
    return txt;
  }

  //Get list of defender in text form
  public String getDefenderText(){
    String out = "";
    for(int i = 0; i < no_level; i++){
      Army a = defender.get(i);
      out += "Level " + i + ": " + a.getUnitNum() + " units\n";
    }
    return out;
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

  public int getFood() {
    return food_produce;
  }

  public int getTech() {
    return tech_produce;
  }

  public int getSize() {
    return size;
  }
}
