package team5.risc.common;

import java.io.Serializable;

import java.util.ArrayList;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  private ArrayList<AreaNode> areas;
  private int num_player;
  private ArrayList<Region> regions;

  public Map(int num_area, int num_player) {
    this.areas = new ArrayList<AreaNode>();
    for (int i = 0; i < num_area; i++) {
      this.areas.add(new AreaNode("area" + i));//, i % num_player));
    }
    this.num_player = num_player;
    
    this.regions = new ArrayList<Region>();
    generateInitRegions();
    
  }

  public ArrayList<String> getAreasName() {
    ArrayList<String> areas_name = new ArrayList<String>();
    for (int i = 0; i < areas.size(); i++) {
      areas_name.add(areas.get(i).getName());
    }
    return areas_name;
  }

  public void generateInitRegions(){
    for (int i=0; i< num_player;i++){
      regions.add(new Region());
    }
    for (int i = 0; i < areas.size(); i++) {
      int region_idx = i%num_player;
      regions.get(region_idx).addArea(areas.get(i));
    }
  }

  public ArrayList<Region> getInitRegions(){
    return regions;
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
