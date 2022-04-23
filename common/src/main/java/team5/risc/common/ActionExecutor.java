package team5.risc.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.HashSet;

public class ActionExecutor {
  public void execute(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    Region sourceRegion = map.getRegionById(sourceNode.getOwnerId());
    int foodNeed = map.calculateMinimumFood(sourceNode, destinationNode, a.num_unit);
    sourceRegion.subFood(foodNeed);

    sourceNode.reduceDefender(a.num_unit, a.lvl);
    destinationNode.increaseDefender(a.num_unit, a.lvl);

    return;
  }

  public void execute(AttackAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);
    Region sourceRegion = map.getRegionById(sourceNode.getOwnerId());
    // int no_unit = a.num_unit;

    // skip if not enough unit
    if (sourceNode.getDefenderUnit(a.lvl) >= a.num_unit) {

      // Reduce no of units from source, add attacker to list
      sourceNode.reduceDefender(a.num_unit, a.lvl);
      destinationNode.addEnemy(new LevelArmy(a.player_id, a.num_unit, a.lvl));
    
      //Reduce food resource
      sourceRegion.subFood(a.num_unit);

      // Get attacking army
      // Army defender = destinationNode.getArmy();
      // Army attacker = new IntArmy(sourceNode.getOwnerId(), no_unit);
    }
  }

  public void execute(UpgradeAction a, Map map) {
    AreaNode area = map.getAreaNodeByName(a.area);
    Region region = map.getRegionById(area.getOwnerId());
    region.upgradeArmy(a.index_army, a.new_lvl, a.no_units, area);
  }

  public void resolveAllCombat(Map map, Combat c, View view1, View view2) {
    ArrayList<Army> winners = new ArrayList<Army>();
    
    for (AreaNode area : map.getAreas()) {
      boolean pos = true;
      AreaNode buffer = area.deepCopy();
      while (!area.noEnemyLeft()) {
        Army defender = area.getBonusDefender(!pos);
        Army attacker = area.getBonusEnemy(pos);
        
        // Do combat, get winner
        Army winner = c.doCombat(defender, attacker);
        if (winner.getOwnerId() == attacker.getOwnerId()) {
          winners.add(winner);
        }
        if(area.noEnemyLeft()){
          combatExecute(defender, attacker, winner, winners, map, area, c, view1, view2, buffer);
        }
        pos = !pos;
      }
    }
  }

  public void setAction(SpyAction a, Map map, int turn){
    a.turn = turn;
    AreaNode src = map.getAreaNodeByName(a.src);
    AreaNode dest = map.getAreaNodeByName(a.dest);
    src.reduceDefender(1);
    a.distance = getDistance(src, dest, map, a.player_id);
    //System.out.println("Distance : " + a.distance);
  }

  public int getDistance(AreaNode src, AreaNode dest, Map map, int id){
    Queue<String> queue = new PriorityQueue<>();
    HashSet<String> visited = new HashSet<>();
    queue.add(src.getName());
    ArrayList<String> areas = dist(src, dest, map, queue, visited);
    int distance = 0;
    for(String area : areas){
      //System.out.println("Name : " + area);
      AreaNode a = map.getAreaNodeByName(area);
      if(a.getOwnerId() != id){
        distance++;
      }
    }
    return distance;
  }

  public ArrayList<String> dist(AreaNode src, AreaNode dest, Map map, Queue<String> queue, HashSet<String> visited){
    visited.add(src.getName());
    ArrayList<String> out = new ArrayList<>();
    HashMap<String, String> mapping = new HashMap<>();

    while(!queue.isEmpty()){
        String name = queue.remove();
        AreaNode a = map.getAreaNodeByName(name);
        if(a == dest){
          break;
        }

        for(AreaNode neigbour : a.getNeighbors()){
          if(!visited.contains(neigbour.getName())){
            mapping.put(neigbour.getName(), name);
            queue.add(neigbour.getName());
            visited.add(neigbour.getName());
          }
        }
    }
    String name = dest.getName();
    while(name != src.getName()){
      String parent = mapping.get(name);
      //System.out.println(name);
      out.add(parent);
      name = parent;
    }
    return out;
  }

  public void execute(CloakAction a, Map map, int turn){
    a.turn = turn;
    AreaNode area = map.getAreaNodeByName(a.area);
    Region region = map.getRegionById(area.getOwnerId());
    region.subTech(a.cost);
    area.setCloaking(true);
  }

  public void execute(SpyAction a, Map map){
    AreaNode src = map.getAreaNodeByName(a.src);
    AreaNode dest = map.getAreaNodeByName(a.dest);
    Region region = map.getRegionById(src.getOwnerId());
    region.subTech(a.cost);
    dest.setSpy(true);
  }

  public void removeCloak(CloakAction a, Map map){
    AreaNode area = map.getAreaNodeByName(a.area);
    area.setCloaking(false);
  }

  public void combatExecute(Army defender, Army attacker, Army winner, ArrayList<Army> winners, Map map, AreaNode dest, Combat c, View view1, View view2, AreaNode buffer) {

    // Do combat, get winner
    // Combat c = new DiceCombat(20, 1);

    // Print winner
    // System.out.println("Combat winner : " + winner.getOwnerId());

    // Update region, areaNode, if attacker wins
    if (winner.getOwnerId() == attacker.getOwnerId()) {
      //check if defending areaNode is empty
      if(!dest.hasLost()){
        dest.setDefender(defender);
        return;
      } 

      buffer.setOwner(attacker.getOwnerId());
      dest.setCloaking(false);
      dest.setSpy(false);
      view1.setBuffer(buffer);
      view2.setBuffer(buffer);

      // Update regions
      map.getRegions().get(defender.getOwnerId()).removeArea(dest);
      map.getRegions().get(attacker.getOwnerId()).addArea(dest);

      // Update areaNode
      for(Army a : winners){
        dest.setDefender(a);
      }
    }
    else{
      dest.setDefender(defender);
    }
  }

  public void addUnitToAllArea(int unit_num, Map map) {
    for (AreaNode a : map.getAreas()) {
      a.increaseDefender(unit_num);
    }
  }

  public void updateResources(Map map) {
    for (Region r : map.getRegions()) {
      r.incFoodTech();
    }
  }

}
