package team5.risc.common;

import java.util.LinkedHashSet;

public class ActionExecutor {
  public void execute(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);
    sourceNode.reduceDefender(a.num_unit);
    destinationNode.increaseDefender(a.num_unit);
  }

  public void execute(AttackAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);
    // skip if no enough unit
    if (sourceNode.getDefenderUnit() >= a.num_unit) {
      //sourceNode.reduceDefender(a.num_unit);
      //destinationNode.addEnemy(new IntArmy(a.player_id, a.num_unit));
      Combat c = new DiceCombat(20, 1);
      Army winner = c.doCombat(destinationNode.getArmy(), sourceNode.getArmy());
      System.out.println("Combat winner : " + winner.getOwnerId());
    }
  }

}
