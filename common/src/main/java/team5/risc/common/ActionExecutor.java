package team5.risc.common;

public class ActionExecutor {
  public void execute(MoveAction a, Map map) {
    a.source.reduceDefender(a.num_unit);
    a.destination.increaseDefender(a.num_unit);
  }

  public void execute(AttackAction a, Map map) {
  }
}
