package team5.risc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  private ArrayList<AreaNode> areas;
  // private HashMap<String, AreaNode> name2areas;
  private int num_player;
  private ArrayList<Region> regions;

  public Map(int num_area) {
    this.areas = new ArrayList<AreaNode>();
    // this.name2areas = new HashMap<>();
    for (int i = 0; i < num_area; i++) {
      AreaNode areaNode = new AreaNode("area" + i);
      this.areas.add(areaNode);
      // this.name2areas.put("area" + i, areaNode);
    }

    // this.regions = new ArrayList<Region>();
    // generateInitRegions();
  }

  public Map(int num_area, int num_player) {
    this.areas = new ArrayList<AreaNode>();
    // this.name2areas = new HashMap<>();
    for (int i = 0; i < num_area; i++) {
      AreaNode areaNode = new AreaNode("area" + i, i % num_player);
      this.areas.add(areaNode);
      // this.name2areas.put("area" + i, areaNode);
    }
    this.num_player = num_player;
    this.regions = new ArrayList<Region>();
    generateInitRegions();
  }

  public Map() {
    this.areas = new ArrayList<AreaNode>();
    // this.name2areas = new HashMap<>();
    int num_area = 9;
    for (int i = 0; i < num_area; i++) {
      AreaNode areaNode = new AreaNode("area" + i);
      this.areas.add(areaNode);
      // this.name2areas.put("area" + i, areaNode);
    }
    generateExampleMap();
    this.num_player = 3;
    this.regions = new ArrayList<Region>();
    generateInitRegions();
  }

  public AreaNode getAreaNodeByName(String name) {
    for (AreaNode area: areas) {
      if (area.getName().equals(name)) {
        return area;
      }
    }
    return null;
    // return this.name2areas.get(name);
  }

  public ArrayList<String> getAreasName() {
    ArrayList<String> areas_name = new ArrayList<String>();
    for (int i = 0; i < areas.size(); i++) {
      areas_name.add(areas.get(i).getName());
    }
    return areas_name;
  }

  public int getNumPlayer() {
    return num_player;
  }

  public void generateExampleMap() {
    // 9 areas, 3 players
    addPath(0, 3);
    addPath(0, 2);
    addPath(2, 3);
    addPath(2, 5);
    addPath(5, 3);
    addPath(2, 8);
    addPath(5, 8);
    addPath(4, 5);
    addPath(5, 6);
    addPath(5, 7);
    addPath(8, 7);
    addPath(6, 7);
    addPath(4, 1);
    addPath(1, 6);
    addPath(4, 6);
  }

  private void addPath(int area1_idx, int area2_idx) {
    areas.get(area1_idx).addNeighbor(areas.get(area2_idx));
    areas.get(area2_idx).addNeighbor(areas.get(area1_idx));
  }

  public void generateInitRegions() {
    for (int i = 0; i < num_player; i++) {
      regions.add(new Region());
    }
    for (int i = 0; i < areas.size(); i++) {
      int region_idx = i % num_player;
      regions.get(region_idx).addArea(areas.get(i));
    }
  }

  public ArrayList<Region> getInitRegions() {
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
