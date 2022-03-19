package team5.risc.common;

import java.io.Serializable;

public abstract class SimpleAction implements Action, Serializable {
  public int player_id;
  public String source;
  public String destination;
  public int num_unit;

  public SimpleAction(int id, 
                      String src,
                      String dst,
                      int unit) {
        this.player_id = id;
        this.source = src;
        this.destination = dst;
        this.num_unit = unit;
    }
}
