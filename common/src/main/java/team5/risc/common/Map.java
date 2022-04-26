package team5.risc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

  public Map(int num_area, int num_player, boolean opt) {
    this.areas = new ArrayList<AreaNode>();
    // this.name2areas = new HashMap<>();
    for (int i = 0; i < num_area; i++) {
      AreaNode areaNode = new AreaNode("area" + i, 2, 3, 1);
      this.areas.add(areaNode);
      // this.name2areas.put("area" + i, areaNode);
    }
    this.num_player = num_player;
    ArrayList<String> path = new ArrayList<String>();

    for(int i = 0; i < 9; i++){
      int src = i;
      int des = i + 3;
      addPath(src, des);
      path.add(src + "->" + des);
      addPath(des, src);
      path.add(des + "->" + src);
    }

    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 2; j++){
        int src = (i*3) + j;
        int des = (i*3) + j + 1;
        addPath(src, des);
        path.add(src + "->" + des);
        addPath(des, src);
        path.add(des + "->" + src);
      }
    }

    this.regions = new ArrayList<Region>();
    regions.add(new Region());
    regions.add(new Region());

    for(int i = 0; i < 6; i++){
      regions.get(0).addArea(areas.get(i));
      regions.get(1).addArea(areas.get(i + 6));
    }
  }

  public Map(int num_area, int num_player) {
    this.areas = new ArrayList<AreaNode>();
    // this.name2areas = new HashMap<>();
    for (int i = 0; i < num_area; i++) {
      AreaNode areaNode = new AreaNode("area" + i, 2, 3, 1);
      this.areas.add(areaNode);
      // this.name2areas.put("area" + i, areaNode);
    }
    this.num_player = num_player;
    generateMap();
    this.regions = new ArrayList<Region>();
    generateInitRegions();
  }

  // for testing
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
    regions.get(0).setOwnerId(0);
    regions.get(1).setOwnerId(1);
    regions.get(2).setOwnerId(2);
    regions.get(0).setInitUnit(getAreaNodeByName("area0"), 10);
    regions.get(0).setInitUnit(getAreaNodeByName("area3"), 12);
    regions.get(0).setInitUnit(getAreaNodeByName("area6"), 14);

    regions.get(1).setInitUnit(getAreaNodeByName("area1"), 13);
    regions.get(1).setInitUnit(getAreaNodeByName("area4"), 8);
    regions.get(1).setInitUnit(getAreaNodeByName("area7"), 3);

    regions.get(2).setInitUnit(getAreaNodeByName("area2"), 6);
    regions.get(2).setInitUnit(getAreaNodeByName("area5"), 5);
    regions.get(2).setInitUnit(getAreaNodeByName("area8"), 3);
   }

   //For testing
   public Map(Boolean opt) {
     this.areas = new ArrayList<AreaNode>();
     // this.name2areas = new HashMap<>();
     int num_area = 6;
     for (int i = 0; i < num_area; i++) {
       AreaNode areaNode = new AreaNode("area" + i, 10, 10, 1);
       this.areas.add(areaNode);
       // this.name2areas.put("area" + i, areaNode);
     }
     this.num_player = 2;
     this.regions = new ArrayList<Region>();
     generateInitRegions();
     regions.get(0).setOwnerId(0);
     regions.get(1).setOwnerId(1);
     regions.get(0).setInitUnit(getAreaNodeByName("area0"), 32);
     regions.get(0).setInitUnit(getAreaNodeByName("area2"), 22);
     regions.get(0).setInitUnit(getAreaNodeByName("area4"), 22);

     regions.get(1).setInitUnit(getAreaNodeByName("area1"), 1);
     regions.get(1).setInitUnit(getAreaNodeByName("area3"), 1);
     regions.get(1).setInitUnit(getAreaNodeByName("area5"), 1);

     addPath(0, 1);
     addPath(0, 2);
     addPath(2, 4);
     addPath(2, 3);
     addPath(5, 4);
     addPath(3, 5);
     addPath(3, 1);
   }

  public AreaNode getAreaNodeByName(String name) {
    for (AreaNode area : areas) {
      if (area.getName().equals(name)) {
        return area;
      }
    }
    return null;
    // return this.name2areas.get(name);
  }

  public Region getRegionById(int id) {
    if (id < regions.size()) {
      return regions.get(id);
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

  public ArrayList<String> generateMap() {
    ArrayList<String> path = new ArrayList<String>();
    int num_area_per = areas.size() / num_player;
    for (int i = 0; i < num_player; i++) {
      for (int j = 0; j < num_area_per - 1; j++) {
        int src = i + j * num_player;
        int des = i + (j + 1) * num_player;
        addPath(src, des);
        path.add(src + "->" + des);
        addPath(des, src);
        path.add(des + "->" + src);
      }
    }
    for (int j = 0; j < num_area_per; j++) {
      for (int i = 0; i < num_player - 1; i++) {
        int src = j * num_player + i;
        int des = j * num_player + i + 1;
        addPath(src, des);
        path.add(src + "->" + des);
        addPath(des, src);
        path.add(des + "->" + src);

      }
    }
    return path;
  }

  int calculateMinimumFood(AreaNode sourceNode, AreaNode destinationNode, int num_unit) {
    HashSet<String> currentSet = new HashSet<>();
    HashMap<String, Integer> node2Distance = new HashMap<>();
    int player_id = sourceNode.getOwnerId();
    // currentSet.add(sourceNode.getName());
    node2Distance.put(sourceNode.getName(), 0);

    while (true) {
      int shortest_cost = 1000000;
      String shortest_name = "";
      for (HashMap.Entry<String, Integer> entry : node2Distance.entrySet()) {
        if (!currentSet.contains(entry.getKey()) &&
            entry.getValue() < shortest_cost) {
          shortest_name = entry.getKey();
          shortest_cost = entry.getValue();
        }
      }
      if (shortest_name.equals(""))
      return -1;
      if (shortest_name.equals(destinationNode.getName())) {
        return num_unit * shortest_cost;
      }

      currentSet.add(shortest_name);
      for (String neighbor : getAreaNodeByName(shortest_name).getNeighborsName()) {
        if (getAreaNodeByName(neighbor).getOwnerId() != player_id)
          continue;

        if (!node2Distance.containsKey(neighbor) ||
            node2Distance.get(neighbor) > shortest_cost + getAreaNodeByName(neighbor).getFood()) {
          node2Distance.put(
              neighbor,
              shortest_cost + getAreaNodeByName(neighbor).getFood());
        }
      }
    }
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

  public ArrayList<Region> getRegions() {
    return regions;
  }

  public ArrayList<AreaNode> getAreas() {
    return areas;
  }

  public boolean is_loser(int id) {
    ArrayList<AreaNode> areas = getAreas();
    for (AreaNode area : areas) {
      if (area.getOwnerId() == id) {
        return false;
      }
    }
    return true;
  }

  public boolean is_winner(int id) {
    ArrayList<AreaNode> areas = getAreas();
    int no = 0;
    for (AreaNode area : areas) {
      if (area.getOwnerId() == id) {
        no++;
      } else {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    String ans = new String();
    for (AreaNode a : areas) {
      ans += a;
      ans += "\n";
    }
    return ans;
  }
}
