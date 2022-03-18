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
    int no_unit = a.num_unit;

    // skip if not enough unit
    if (sourceNode.getDefenderUnit() >= a.num_unit) {

      //Reduce no of units from source, add attacker to list
      sourceNode.reduceDefender(a.num_unit);
      destinationNode.addEnemy(new IntArmy(a.player_id, a.num_unit));

      //Get attacking army
      Army defender = destinationNode.getArmy();
      Army attacker = new IntArmy(sourceNode.getOwnerId(), no_unit);
      }
  }
  

  public void CombatExecute(Army defender,Army attacker, Map map, AreaNode dest){
  
      //Do combat, get winner
      Combat c = new DiceCombat(20, 1);
      Army winner = c.doCombat(defender, attacker);

      //Print winner
      System.out.println("Combat winner : " + winner.getOwnerId());

      //Update region, areaNode, if attacker wins
      if(winner.getOwnerId() == attacker.getOwnerId()){
        //Update areaNode
        dest.setDefender(winner);

        //Update regions
        map.getInitRegions().get(defender.getOwnerId()).removeArea(dest);
        map.getInitRegions().get(attacker.getOwnerId()).addArea(dest);
      }
  }

}