package team5.risc.common;

import java.io.Serializable;

public abstract class SimpleAction implements Action, Serializable {
  public int player_id;
  public String source;
  public String destination;
  public int num_unit;
  public Boolean is_terminated;

  public SimpleAction(int id, 
                      String src,
                      String dst,
                      int unit,
                      Boolean terminated) {
        this.player_id = id;
        this.source = src;
        this.destination = dst;
        this.is_terminated = terminated;
    }
}
