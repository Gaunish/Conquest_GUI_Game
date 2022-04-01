package team5.risc.common;

import java.util.HashSet;

public class ActionValidator {

  // Validate move action
  public String isValid(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    if (sourceNode == null) {
      return "Invalid source name";
    }
    if (destinationNode == null) {
      return "Invalid destination name";
    }
    if (a.player_id != sourceNode.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + sourceNode.getName() + ", which is owned by Player "
          + sourceNode.getOwnerId();
    }
    if (sourceNode.getOwnerId() != destinationNode.getOwnerId()) {
      return "Player " + a.player_id + " can't move unit to " + destinationNode.getName()
          + ", which is owned by Player " + destinationNode.getOwnerId();
    }

    // Check unit num with specific level
    if (sourceNode.getDefenderUnit(a.lvl) < a.num_unit) {
      return sourceNode.getName() + " doesn't have enough unit (level " + a.lvl + ") to move";
    }

    // Check reachability
    Boolean reachable = checkReachable(sourceNode, destinationNode, a.player_id);
    if (!reachable) {
      return "Unreachable";
    }

    // Check Food
    Region sourceRegion = map.getRegionById(sourceNode.getOwnerId());

    int foodNeed = map.calculateMinimumFood(sourceNode, destinationNode, a.num_unit);
    if (!sourceRegion.checkFoodEnough(foodNeed)) {
      return "Not enough food";
    }

    return null;
  }

  public String isValid(AttackAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    if (sourceNode == null) {
      return "Invalid source name";
    }
    if (destinationNode == null) {
      return "Invalid destination name";
    }

    if (a.player_id != sourceNode.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + sourceNode.getName() + ", which is owned by Player "
          + sourceNode.getOwnerId();
    }

    if (sourceNode.getOwnerId() == destinationNode.getOwnerId()) {
      return destinationNode.getName() + " also belongs to Player " + destinationNode.getOwnerId()
          + ", please attack other players' areas";
    }

    // Check reachability
    Boolean reachable = sourceNode.getNeighbors().contains(destinationNode);
    if (!reachable) {
      return "Unreachable";
    }
    return null;
  }

  public String isValid(UpgradeAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.area);
    if (sourceNode == null) {
      return "Invalid source name";
    }

    // check ownership
    if (a.player_id != sourceNode.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + sourceNode.getName() + ", which is owned by Player "
          + sourceNode.getOwnerId();
    }

    // Check unit num with specific level
    Army to_upgrade = sourceNode.getDefenderByIndex(a.index_army);
    int cur_level = to_upgrade.getLevel();
    int act_unit_num = to_upgrade.getUnitNum();
    Region sourceRegion = map.getRegionById(sourceNode.getOwnerId());

    if (act_unit_num < a.no_units) {
      return sourceNode.getName() + " only have " + act_unit_num + " unit (level " + cur_level + ")";
    }

    // check tech resource
    int need_tech = a.no_units * sourceNode.cost(cur_level, a.new_lvl);
    if (sourceRegion.checkTechEnough(need_tech) == false) {
      return "no enough tech";
    }

    // check max tech level
    if (a.new_lvl > 6) {
      return "max tech level is 6";
    }

    // check level
    if (a.new_lvl <= cur_level) {
      return "those units are already in level " + cur_level;
    }

    return null;
  }

  public Boolean checkReachable(AreaNode src, AreaNode dest, int player_id) {
    HashSet<String> visited = new HashSet<>();
    return dfs(src, dest, player_id, visited);
  }

  public Boolean dfs(AreaNode src, AreaNode dest, int player_id, HashSet<String> visited) {
    if (visited.contains(src.getName()))
      return false;
    visited.add(src.getName());

    if (src.getName().equals(dest.getName()))
      return true;
    for (AreaNode node : src.getNeighbors()) {
      if (node.getOwnerId() == player_id) {
        Boolean flag = dfs(node, dest, player_id, visited);
        if (flag == true)
          return true;
      }
    }
    return false;
  }
}
