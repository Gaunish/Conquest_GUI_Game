package team5.risc.client;

import team5.risc.common.*;
import java.io.PrintStream;
import java.util.ArrayList;

public class TextDisplayMap implements Display {
  private PrintStream out;

  public TextDisplayMap(PrintStream output){
    this.out = output;
  }

  //Display map 
  public void display(Map m){
    String display_str = getRegions(m);
    out.println(display_str);
  }

  private String getRegion(int id, ArrayList<AreaNode> areas) {
    String out = "";
    out += "Player " + id + ":\n";
    for (int i = 0; i < areas.size(); i++) {
      AreaNode area = areas.get(i);
      if(area.getOwnerId() == id){
        out += area.getDefenderUnit() + " units in " + area.getName() + "\n";
      }
    }
    return out;
  }

  private String getRegions(Map m){
    String regions = "";
    int num_player = m.getNumPlayer();
    ArrayList<AreaNode> areas = m.getAreas();
      
    for(int i = 0; i < num_player; i++){
      regions += getRegion(i, areas);
    }
    return regions;
  }


}
