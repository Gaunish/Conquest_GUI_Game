package team5.risc.common;

import java.io.Serializable;

import java.util.ArrayList;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  public ArrayList<AreaNode> areas;

  public Map(int num_area) {
    this.areas = new ArrayList<AreaNode>();
    for (int i = 0; i < num_area; i++) {
      this.areas.add(new AreaNode("area" + i));
    }
  }

  public ArrayList<String> getAreasName() {
    ArrayList<String> areas_name = new ArrayList<String>();
    for (int i = 0; i < areas.size(); i++) {
      areas_name.add(areas.get(i).getName());
    }
    return areas_name;
  }

  // Displays map
  // currently coupled to system.out
  public void displayMap() {
    System.out.println("Hello World");

  }
}
