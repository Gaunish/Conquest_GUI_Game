package team5.risc.client;

import team5.risc.common.*;

public interface Input {
  public String getAction(String name);
  public int getPlacement(String area);
  public MoveAction getMove();
}
