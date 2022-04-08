package team5.risc.common;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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

  public void resolveAllCombat(Map map, Combat c) {
    ArrayList<Army> winners = new ArrayList<Army>();
    
    for (AreaNode area : map.getAreas()) {
      boolean pos = true;
      while (!area.noEnemyLeft()) {
        Army defender = area.getBonusDefender(!pos);
        Army attacker = area.getBonusEnemy(pos);
        
        // Do combat, get winner
        Army winner = c.doCombat(defender, attacker);
        if (winner.getOwnerId() == attacker.getOwnerId()) {
          winners.add(winner);
        }
        if(area.noEnemyLeft()){
          combatExecute(defender, attacker, winner, winners, map, area, c);
        }
        pos = !pos;
      }
    }
  }

  public void combatExecute(Army defender, Army attacker, Army winner, ArrayList<Army> winners, Map map, AreaNode dest, Combat c) {

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
