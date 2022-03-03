package team5.risc.common;

import java.io.Serializable;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  public int num_players;
  public int num_territories;
  

  public Map(int players, int t) {
    this.num_players = players;
    this.num_territories = t;
  }

  //Displays map
  //currently coupled to system.out
  public void displayMap() {
  System.out.println("Hello World");
  }
}
