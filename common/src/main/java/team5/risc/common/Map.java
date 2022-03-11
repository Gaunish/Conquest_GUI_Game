package team5.risc.common;

import java.io.Serializable;

import java.util.ArrayList;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  public ArrayList<AreaNode> areas;
  public int num_player;

  public Map(int num_area, int num_player) {
    this.areas = new ArrayList<AreaNode>();
    for (int i = 0; i < num_area; i++) {
      this.areas.add(new AreaNode("area" + i, i % num_player));
    }
    this.num_player = num_player;
  }

  public ArrayList<String> getAreasName() {
    ArrayList<String> areas_name = new ArrayList<String>();
    for (int i = 0; i < areas.size(); i++) {
      areas_name.add(areas.get(i).getName());
    }
    return areas_name;
  }
  
  public String getRegion(int id) {
    String out = "";
    out += "Player " + id + ":\n";
    for (int i = 0; i < areas.size(); i++) {
      AreaNode area = areas.get(i);
      if(area.getOwnerId() == id){
        out += area.getName() + "\n";
      }
    }
    return out;
  }

  public String getRegions(){
    String regions = "";
    for(int i = 0; i < num_player; i++){
      regions += getRegion(i);
    }
    return regions;
  }

   public ArrayList<AreaNode> getAreas() {
    return areas;
  }
 
  // Displays map
  // currently coupled to system.out
  public void displayMap() {
    System.out.println("Hello World");

  }

}
