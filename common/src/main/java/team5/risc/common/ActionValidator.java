package team5.risc.common;
import java.util.HashSet;

public class ActionValidator {
  public String isValid(MoveAction a, Map map) {
    
    if (a.player_id != a.source.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + a.source.getName() + ", which is owned by Player "
          + a.source.getOwnerId();
    }
    if (a.source.getOwnerId() != a.destination.getOwnerId()) {
      return a.destination.getName() + " belongs to Player" + a.destination.getOwnerId();
    }
    if (a.source.getDefenderUnit() < a.num_unit) {
      return a.source.getName() + "doesn't have enough unit to move";
    }

    //Check reachability
    Boolean reachable = checkReachable(a.source, a.destination, a.player_id);
    if (reachable!= null) return "Unreachale";

    return null;
  }

  public String isValid(AttackAction a, Map map) {
    return null;
  }

  public Boolean checkReachable(AreaNode src, AreaNode dest, int player_id) {
    HashSet<String> visited = new HashSet<>();
    return dfs(src, dest, player_id, visited);
  }

  public Boolean dfs(AreaNode src, AreaNode dest, int player_id, HashSet<String> visited) {
    if (visited.contains(src.getName())) return false;
    visited.add(src.getName());

    if (src.getName().equals(dest.getName())) return true;
    for (AreaNode node : src.getNeighbors()) {
      if (node.getOwnerId() == player_id) {
        Boolean flag = dfs(node, dest, player_id, visited);
        if (flag == true) return true;
      }
    }
    return false;
  }
}
