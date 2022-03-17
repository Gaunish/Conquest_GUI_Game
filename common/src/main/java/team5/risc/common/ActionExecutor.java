package team5.risc.common;

public class ActionExecutor {
  public void execute(MoveAction a, Map map) {
    AreaNode sourceNode = map.getAreaNodeByName(a.source);
    AreaNode destinationNode = map.getAreaNodeByName(a.destination);
    sourceNode.reduceDefender(a.num_unit);
    destinationNode.increaseDefender(a.num_unit);
  }

  public void execute(AttackAction a, Map map) {
  }
}
