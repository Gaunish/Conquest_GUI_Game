package team5.risc.common;

import java.util.LinkedHashSet;

public class ActionExecutor {
  public void execute(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    Region sourceRegion = map.getRegionById(sourceNode.getOwnerId());
    int foodNeed = map.calculateMinimumFood(sourceNode, destinationNode, a.num_unit);
    sourceRegion.subFood(foodNeed);

    sourceNode.reduceDefender(a.num_unit);
    destinationNode.increaseDefender(a.num_unit);

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
      sourceNode.reduceDefender(a.num_unit);
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
    for (AreaNode area : map.getAreas()) {
      while (!area.noEnemyLeft()) {
        Army defender = area.getDefender();
        Army attacker = area.popFirstEnemy();
        combatExecute(defender, attacker, map, area, c);
      }
    }
  }

  public void combatExecute(Army defender, Army attacker, Map map, AreaNode dest, Combat c) {

    // Do combat, get winner
    // Combat c = new DiceCombat(20, 1);
    Army winner = c.doCombat(defender, attacker);

    // Print winner
    // System.out.println("Combat winner : " + winner.getOwnerId());

    // Update region, areaNode, if attacker wins
    if (winner.getOwnerId() == attacker.getOwnerId()) {
      // Update areaNode
      dest.setDefender(winner);

      // Update regions
      map.getRegions().get(defender.getOwnerId()).removeArea(dest);
      map.getRegions().get(attacker.getOwnerId()).addArea(dest);
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
