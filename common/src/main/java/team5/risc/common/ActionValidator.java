package team5.risc.common;

import java.util.HashSet;

public class ActionValidator {

  //Validate move action
  public String isValid(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    if (a.player_id != sourceNode.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + sourceNode.getName() + ", which is owned by Player "
          + sourceNode.getOwnerId();
    }
    if (sourceNode.getOwnerId() != destinationNode.getOwnerId()) {
      return destinationNode.getName() + " belongs to Player" + destinationNode.getOwnerId();
    }
    if (sourceNode.getDefenderUnit() < a.num_unit) {
      return sourceNode.getName() + "doesn't have enough unit to move";
    }

    // Check reachability
    Boolean reachable = checkReachable(sourceNode, destinationNode, a.player_id);
    if (!reachable) {
      return "Unreachale";
    }
    return null;
  }

  public String isValid(AttackAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);

    if (a.player_id != sourceNode.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + sourceNode.getName() + ", which is owned by Player "
          + sourceNode.getOwnerId();
    }

    if (sourceNode.getOwnerId() == destinationNode.getOwnerId()) {
      return destinationNode.getName() + " also belongs to Player" + destinationNode.getOwnerId()
          + ", please attack other players' areas";
    }

    // Check reachability
    Boolean reachable = sourceNode.getNeighbors().contains(destinationNode);
    if (!reachable) {
      return "Unreachable";
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
