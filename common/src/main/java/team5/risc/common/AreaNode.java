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

  public void setDefender(Army new_defender) {
    setDefender(new_defender, 0);
  }

  //set given lvl defender
  public void setDefender(Army new_defender, int lvl) {
    defender.set(lvl, new_defender);
  }

  public Army getDefender() {
    return getDefender(0);
  }

  //Get given lvl defender
  public Army getDefender(int lvl) {
    return defender.get(lvl);
  }

  public Army getDefenderByIndex(int idex){
    return defender.get(idex);
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
    txt += "Food production: " + food_produce + "\n";
    txt += "Tech production: " + tech_produce + "\n";
    txt += "Size: " + size + "\n\n";
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
