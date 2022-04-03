package team5.risc.client;

import java.io.IOException;

import team5.risc.common.*;

public interface Input {
  public String getAction(String name);
  public int getPlacement(String area);
  public MoveAction getMove(int id);
  public AttackAction getAttack(int id);
  public String getLoser();
  public String getArea();
  public UpgradeAction getUpgrade(int id, String area);
}
