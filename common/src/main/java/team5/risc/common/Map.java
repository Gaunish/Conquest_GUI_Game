package team5.risc.common;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.checkerframework.checker.units.qual.Area;

public class Map implements Serializable {
  private static final long serialVersionUID = 1L;
  public ArrayList<AreaNode> areas;  
  public int num_player;
  public ArrayList<ArrayList<AreaNode>> actualMap;

  public Map(int num_area, int num_player) {
    this.areas = new ArrayList<AreaNode>();
    for (int i = 0; i < num_area; i++) {
      //i % num_player ensures that each person has a "unique" area
      this.areas.add(new AreaNode("area" + i, i % num_player));
    }
    this.num_player = num_player;
    this.actualMap = new ArrayList<ArrayList<AreaNode>>();
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
 /*
    This function returns the list of list of neighbors a player has
    @params a player's id
    @returns arraylist of neighbors in string format
 */
  public ArrayList<ArrayList<String>> getNeighbors(int id){
    ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    for(int i = 0; i < areas.size(); i++){
      AreaNode area = areas.get(i);
      if(area.getOwnerId() == id){
        result.add(area.getNeighborsName());
      }
    }
    return result;
  }
  /*
    This function returns a list of areas names that belong to a player
  */
  public ArrayList<String> getAreaString(int id){
    ArrayList<String> result = new ArrayList<String>();
    for (int i = 0; i < areas.size(); i++) {
      AreaNode area = areas.get(i);
      if(area.getOwnerId() == id){
        result.add(area.getName());
      }
    }
    return result;
  }

  /*
    This function returns a list of areas that belong to a player
  */
  public ArrayList<AreaNode> getAreas(int id){
    ArrayList<AreaNode> result = new ArrayList<AreaNode>();
    for (int i = 0; i < areas.size(); i++) {
      AreaNode area = areas.get(i);
      if(area.getOwnerId() == id){
        result.add(area);
      }
    }
    return result;
  }

  /*
    This function is responsible for creating a simple map.
    It assumes that each player has the same number of players.
    Hence number_of_areas/number_of_players
  */
  public void createSimpleMap(){
    int areasPerPlayer = areas.size() / num_player;
    for(int i = 0; i < num_player; i++){

      //This next line is to be changed just in case we end up using a 
      //list or something for client ids
      int currentPlayerID = areas.get(i * areasPerPlayer).getOwnerId();

      actualMap.add(this.getAreas(currentPlayerID));
    }
    this.assignNeighbors();
  }
  /*
    This function displays a simple map, where row 0 has areas of player 0, row 1 for player 1 and so on.
  */
  public void displaySimpleMap(){
    for(int i = 0; i < actualMap.size(); i++){
      for(int j = 0; j < actualMap.get(i).size(); j++){
        if(j != actualMap.get(i).size() - 1){ // This branch can be removed. If we are facing performance issues somehow          
          System.out.print(actualMap.get(i).get(j).getName() + "\t");
        }
      } 
      System.out.print("\n");
    }
  }

  /*
    This function is to be used after creating a map for the game. It will assign neighbors to all regions.
    All regions connected to a particular region are its neigbors. A region can have a maximum of 4 neighbors 
    and a minimum of 2 neighbors.
  */
  public void assignNeighbors(){
    Integer[][] indices = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    for(int i = 0; i < actualMap.size(); i++){
      for(int j = 0; j < actualMap.get(i).size(); j++){
        AreaNode currentArea  = actualMap.get(i).get(j);
        for(int step = 0; step < 4; step++){
          Integer newX = i - indices[step][0];
          Integer newY = j - indices[step][1];
          if( newX >= 0 && newY >= 0){
            currentArea.addNeighbor(actualMap.get(newX).get(newY));
          }
        }
      }
    }
  }
  // Displays map
  // currently coupled to system.out
  public void displayMap() {
    System.out.println("Hello World");
  }

}
// public void displayAdjacentInformation(Map map, int id){
  //   ArrayList<String> areas = map.getAreas(id);
  //   ArrayList<ArrayList<String>> neighbors = map.getNeighbors(id);
  //   for(int i = 0; i < areas.size(); i++){
  //     System.out.println(areas.get(i) + "(next to: ");
  //     ArrayList<String> currentNeighbors = neighbors.get(i);
  //     for(int j = 0; j < currentNeighbors.size(); j++){
  //       System.out.print(currentNeighbors.get(j));
  //       if(j != currentNeighbors.size() - 1){
  //         System.out.print(", ");
  //       }
  //     }
  //     System.out.print(" )");
  //   }
  // }